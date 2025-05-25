package com.finance.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.BudgetDAO;
import com.finance.dao.TransactionDAO;
import com.finance.entities.Account;
import com.finance.entities.Budget;
import com.finance.entities.Category;
import com.finance.entities.Transaction;

@Service
public class TransactionService {
	private final TransactionDAO transactionDAO;
	private final CategoryService categoryService;
	private final AccountService accountService;
	private final BudgetService budgetService;
	private final BudgetDAO budgetDAO;
	
	@Autowired
    public TransactionService(TransactionDAO transactionDAO, CategoryService categoryService,
                              AccountService accountService, BudgetService budgetService, BudgetDAO budgetDAO) {
        this.transactionDAO = transactionDAO;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.budgetDAO = budgetDAO;
    }
	
	public Transaction addTransaction(String type, double amount, LocalDate date, int categoryId, String description, int accountId) {
        if (type == null || amount < 0 || date == null) {
            throw new IllegalArgumentException("Invalid transaction parameters");
        }
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        Transaction transaction = new Transaction(0, type, amount, date, category, description, account);
        int newId = transactionDAO.saveTransaction(transaction);
        transaction.setId(newId);

        accountService.updateBalance(accountId, amount, type);

        if ("expense".equalsIgnoreCase(type)) {
            Budget budget = budgetService.getBudgetByCategoryId(categoryId);
            if (budget != null) {
                budget.setSpent(budget.getSpent() + amount);
                budgetDAO.saveBudget(budget);
            }
        }
        return transaction;
    }
	
	public Transaction getTransactionById(int id) {
        return transactionDAO.getTransactionById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
	
    public void updateTransaction(int id, String type, double amount, LocalDate date, int categoryId, String description, int accountId) {
        Transaction existingTransaction = getTransactionById(id);
        if (existingTransaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        accountService.updateBalance(existingTransaction.getAccount().getId(), existingTransaction.getAmount(),
                existingTransaction.getType().equalsIgnoreCase("income") ? "expense" : "income");

        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        if ("expense".equalsIgnoreCase(existingTransaction.getType()) || "expense".equalsIgnoreCase(type)) {
            Budget budget = budgetService.getBudgetByCategoryId(categoryId);
            if (budget != null) {
                if ("expense".equalsIgnoreCase(existingTransaction.getType())) {
                    budget.setSpent(budget.getSpent() - existingTransaction.getAmount());
                }
                if ("expense".equalsIgnoreCase(type)) {
                    budget.setSpent(budget.getSpent() + amount);
                }
                budgetDAO.saveBudget(budget);
            }
        }

        accountService.updateBalance(accountId, amount, type);

        existingTransaction.setType(type);
        existingTransaction.setAmount(amount);
        existingTransaction.setDate(date);
        existingTransaction.setCategory(category);
        existingTransaction.setDescription(description);
        existingTransaction.setAccount(account);
        transactionDAO.saveTransaction(existingTransaction);
    }
	
    public void deleteTransaction(int id) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        accountService.updateBalance(transaction.getAccount().getId(), transaction.getAmount(),
                transaction.getType().equalsIgnoreCase("income") ? "expense" : "income");

        if ("expense".equalsIgnoreCase(transaction.getType())) {
            Budget budget = budgetService.getBudgetByCategoryId(transaction.getCategory().getId());
            if (budget != null) {
                budget.setSpent(budget.getSpent() - transaction.getAmount());
                budgetDAO.saveBudget(budget);
            }
        }
        transactionDAO.deleteTransaction(id);
    }
	
    public double getTotalIncome() {
        return getAllTransactions().stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {
        return getAllTransactions().stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getRemainingBudget() {
        return budgetService.getRemainingBudget();
    }
    
    public Map<String, Double> getMonthlyReport(int year, int month) {
        List<Transaction> transactions = transactionDAO.getAllTransactions().stream()
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonthValue() == month)
                .collect(Collectors.toList());

        Map<String, Double> report = new HashMap<>();
        double totalIncome = transactions.stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
        double totalExpenses = transactions.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        report.put("income", totalIncome);
        report.put("expenses", totalExpenses);
        return report;
    }
    
    public Map<String, Double> getYearlyReport(int year) {
        List<Transaction> transactions = transactionDAO.getAllTransactions().stream()
                .filter(t -> t.getDate().getYear() == year)
                .collect(Collectors.toList());

        Map<String, Double> report = new HashMap<>();
        double totalIncome = transactions.stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
        double totalExpenses = transactions.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        report.put("income", totalIncome);
        report.put("expenses", totalExpenses);
        return report;
    }
    
    public Map<String, Double> getExpenseByCategory() {
        return transactionDAO.getAllTransactions().stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}

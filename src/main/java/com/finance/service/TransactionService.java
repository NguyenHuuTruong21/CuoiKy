package com.finance.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.BudgetDAO;
import com.finance.dao.TransactionDAO;
import com.finance.entities.Account;
import com.finance.entities.Budget;
import com.finance.entities.Category;
import com.finance.entities.Reminder;
import com.finance.entities.Status;
import com.finance.entities.Transaction;

@Service
public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final BudgetService budgetService;
    private final BudgetDAO budgetDAO;
    private final ReminderService reminderService;

    @Autowired
    public TransactionService(TransactionDAO transactionDAO, CategoryService categoryService,
                              AccountService accountService, BudgetService budgetService, 
                              BudgetDAO budgetDAO, ReminderService reminderService) {
        this.transactionDAO = transactionDAO;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.budgetDAO = budgetDAO;
        this.reminderService = reminderService;
    }

    public int saveTransaction(Transaction transaction, int userId) {
        if (transaction.getType() == null || transaction.getDate() == null || transaction.getCategory() == null) {
            throw new IllegalArgumentException("Type, date, and category must not be null");
        }

        // Kiểm tra số dư tài khoản nếu là giao dịch chi tiêu
        if ("expense".equalsIgnoreCase(transaction.getType())) {
            Account account = accountService.getAccountById(transaction.getAccountId(), userId);
            if (account == null) {
                throw new IllegalArgumentException("Account not found");
            }
            double currentBalance = account.getBalance();
            double totalExpenses = getTotalExpenses(transaction.getAccountId(), userId);
            double availableBalance = currentBalance - totalExpenses;

            if (availableBalance < transaction.getAmount()) {
                throw new IllegalArgumentException("Số dư trong tài khoản của bạn không đủ");
            }
        }

        // Thêm giao dịch
        int transactionId = transactionDAO.saveTransaction(transaction);

        // Cập nhật số dư tài khoản
        accountService.updateBalance(transaction.getAccountId(), userId, transaction.getAmount(), transaction.getType());

        // Cập nhật trạng thái Budget nếu giao dịch là income và liên quan đến category có budget
        if ("income".equalsIgnoreCase(transaction.getType())) {
            updateBudgetStatusAfterTransaction(transaction, userId);
        }

        return transactionId;
    }

    public Transaction getTransactionById(int userId, int id) {
        return transactionDAO.getTransactionById(userId, id);
    }

    public List<Transaction> getAllTransactions(int userId) {
        return transactionDAO.getAllTransactions(userId);
    }

    public void updateTransaction(int userId, int id, String type, double amount, LocalDate date, int categoryId, String description, int accountId) {
        if (type == null || date == null || categoryId == 0) {
            throw new IllegalArgumentException("Type, date, and category must not be null");
        }
        Transaction existingTransaction = getTransactionById(userId, id);
        if (existingTransaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // Lấy thông tin trước khi cập nhật
        double oldAmount = existingTransaction.getAmount();
        String oldType = existingTransaction.getType();
        int oldAccountId = existingTransaction.getAccountId();
        int oldCategoryId = existingTransaction.getCategory().getId();

        // Cập nhật giao dịch
        transactionDAO.updateTransaction(userId, id, type, amount, date, categoryId, description, accountId);

        // Cập nhật số dư tài khoản
        if (oldAccountId != accountId || !oldType.equals(type) || oldAmount != amount) {
            accountService.updateBalance(oldAccountId, userId, oldAmount, oldType.equals("income") ? "expense" : "income");
            accountService.updateBalance(accountId, userId, amount, type);
        } else if (!oldType.equals(type) || oldAmount != amount) {
            accountService.updateBalance(accountId, userId, amount - oldAmount, type);
        }

        // Cập nhật trạng thái Budget nếu giao dịch là income
        Transaction updatedTransaction = getTransactionById(userId, id);
        if ("income".equalsIgnoreCase(updatedTransaction.getType())) {
            updateBudgetStatusAfterTransaction(updatedTransaction, userId);
        }
    }

    public void deleteTransaction(int userId, int id) {
        Transaction transaction = getTransactionById(userId, id);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // Cập nhật số dư tài khoản
        accountService.updateBalance(transaction.getAccountId(), userId, transaction.getAmount(),
                transaction.getType().equalsIgnoreCase("income") ? "expense" : "income");

        // Xóa giao dịch
        transactionDAO.deleteTransaction(userId, id);

        // Cập nhật trạng thái Budget nếu giao dịch là income
        if ("income".equalsIgnoreCase(transaction.getType())) {
            updateBudgetStatusAfterTransaction(null, userId);
        }
    }

    public double getTotalIncome(int userId) {
        return getAllTransactions(userId).stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalIncomeByCategory(int categoryId, int userId) {
        return getAllTransactions(userId).stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()) && t.getCategory().getId() == categoryId)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses(int accountId, int userId) {
        return transactionDAO.getTotalExpensesByAccount(accountId, userId);
    }

    public double getTotalExpenses(int userId) {
        List<Transaction> transactions = getAllTransactions(userId);
        return transactions.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    //lọc theo tháng/năm thu nhập
    public double getTotalIncome(int userId, Integer year, Integer month) {
        return getAllTransactions(userId).stream()
            .filter(t -> "income".equalsIgnoreCase(t.getType()))
            .filter(t -> (year == null || t.getDate().getYear() == year))
            .filter(t -> (month == null || t.getDate().getMonthValue() == month))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    //lọc theo tháng/năm chi tiêu
    public double getTotalExpenses(int userId, Integer year, Integer month) {
        return getAllTransactions(userId).stream()
            .filter(t -> "expense".equalsIgnoreCase(t.getType()))
            .filter(t -> (year == null || t.getDate().getYear() == year))
            .filter(t -> (month == null || t.getDate().getMonthValue() == month))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }



    public double getRemainingBudget(int userId) {
        return budgetService.getRemainingBudget(userId);
    }

    public Map<String, Double> getMonthlyReport(int userId, int year, int month) {
        List<Transaction> transactions = transactionDAO.getAllTransactions(userId).stream()
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

    public Map<String, Double> getYearlyReport(int userId, int year) {
        List<Transaction> transactions = transactionDAO.getAllTransactions(userId).stream()
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

    public Map<String, Double> getExpenseByCategory(int userId) {
        return transactionDAO.getAllTransactions(userId).stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
    
    public Map<String, Double> getIncomeByCategory(int userId) {
        return getAllTransactions(userId).stream()
            .filter(t -> "income".equalsIgnoreCase(t.getType()))
            .collect(Collectors.groupingBy(
                t -> t.getCategory().getName(),
                Collectors.summingDouble(Transaction::getAmount)
            ));
    }

    private void updateBudgetStatusAfterTransaction(Transaction transaction, int userId) {
        List<Budget> budgets = budgetService.getAllBudgets(userId);
        for (Budget budget : budgets) {
            int categoryId = budget.getCategory().getId();
            // Tính số tiền tích lũy từ các giao dịch income của category này
            double accumulatedAmount = getTotalIncomeByCategory(categoryId, userId);

            // Lấy trạng thái hiện tại
            Status oldStatus = budget.getStatus();

            // Cập nhật trạng thái
            budgetService.updateBudgetStatus(budget, accumulatedAmount);

            // Nếu trạng thái chuyển sang Done, tạo Reminder
            if (budget.getStatus() == Status.Done && oldStatus != Status.Done) {
                Reminder reminder = new Reminder(0, "Mục tiêu hoàn thành", budget.getAmount(), 
                        LocalDate.now(), false, false, userId);
                reminder.setBillName("Bạn đã tiết kiệm đủ tiền!");
                reminderService.saveReminder(reminder);
            }
        }
    }
    // Tính tổng số tiền đã chi tiêu (expense) cho một danh mục (category) của user
    public double getTotalExpensesByCategory(int categoryId, int userId) {
        return getAllTransactions(userId).stream()
                .filter(t -> t.getCategory().getId() == categoryId)
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(t -> t.getAmount())
                .sum();
    }
}
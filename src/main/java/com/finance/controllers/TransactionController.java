package com.finance.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Transaction;
import com.finance.service.AccountService;
import com.finance.service.CategoryService;
import com.finance.service.TransactionService;

@Controller
public class TransactionController {
	private final TransactionService transactionService;
	private final CategoryService categoryService;
	private final AccountService accountService;
	
	@Autowired
	public TransactionController(
			TransactionService transactionService, CategoryService categoryService, AccountService accountService) {
		this.transactionService = transactionService;
		this.categoryService = categoryService;
		this.accountService = accountService;
	}
	
	@GetMapping("/transactions")
	public String listTransactions(Model model) {
		model.addAttribute("transactions", transactionService.getAllTransactions());
		return "transaction_list";
	}
	
	@GetMapping("/transaction/add")
	public String showAddForm(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("transaction", new Transaction());
        return "transaction_form";
	}
	
	@PostMapping("/transaction/add")
	public String addTransaction(
			@RequestParam String type,
			@RequestParam double amount,
			@RequestParam String date,
			@RequestParam int categoryId,
			@RequestParam int accountId,
			@RequestParam(required = false) String description, 
			Model model) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			transactionService.addTransaction(type, amount, localDate, categoryId, description, accountId);
			return "redirect:/transactions";
		} catch (Exception e) {
			model.addAttribute("error", "Error adding transaction: " + e.getMessage());
			return "transaction_form";
		}
	}
	
	@GetMapping("/transaction/edit")
	public String showEditForm(
			@RequestParam int id, 
			Model model) {
		Transaction transaction = transactionService.getTransactionById(id);
		if(transaction == null) {
			model.addAttribute("error", "Transaction not found");
			return "redirect:/transactions";
		}
		model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("transaction", transaction);
        return "transaction_form";
	}
	
	@PostMapping("/transaction/update")
	public String updateTransaction(
			@RequestParam int id,
			@RequestParam String type,
			@RequestParam double amount,
			@RequestParam String date,
			@RequestParam int categoryId,
			@RequestParam int accountId,
			@RequestParam(required = false) String description,
			Model model) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			transactionService.updateTransaction(id, type, amount, localDate, categoryId, description, accountId);
			return "redirect:/transactions";
		} catch (Exception e) {
			model.addAttribute("error", "Error updating transaction: " + e.getMessage());
			return "transaction_form";
		}
	}
	
	@GetMapping("/transaction/delete")
	public String deleteTransaction(
			@RequestParam int id,
			Model model) {
		try {
			transactionService.deleteTransaction(id);
			return "redirect:/transaction";
		} catch (Exception e) {
			model.addAttribute("error", "Error deleting transaction: " + e.getMessage());
			return "redirect:/transactions";
		}
	}
}

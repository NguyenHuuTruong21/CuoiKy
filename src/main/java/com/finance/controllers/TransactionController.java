package com.finance.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Account;
import com.finance.entities.Category;
import com.finance.entities.Transaction;
import com.finance.entities.User;
import com.finance.service.AccountService;
import com.finance.service.CategoryService;
import com.finance.service.TransactionService;

import jakarta.servlet.http.HttpSession;

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
	
	@GetMapping("/transaction/add")
    public String showAddForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("categories", categoryService.getAllCategories(userId));
        model.addAttribute("accounts", accountService.getAllAccounts(userId));
        model.addAttribute("transaction", new Transaction());
        return "transaction_form";
    }
	
	@PostMapping("/transaction/add")
    public String addTransaction(
            @RequestParam("accountId") int accountId,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("amount") double amount,
            @RequestParam("type") String type,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        try {
            // Kiểm tra số dư trước khi lưu giao dịch
            double currentBalance = accountService.getAccountBalance(accountId, userId);
            double totalExpenses = transactionService.getTotalExpenses(accountId, userId);
            double availableBalance = currentBalance - totalExpenses;

            if (type.equals("expense") && (availableBalance - amount < 0)) {
                model.addAttribute("error", "Số dư trong tài khoản của bạn không đủ");
                model.addAttribute("categories", categoryService.getAllCategories(userId));
                model.addAttribute("accounts", accountService.getAllAccounts(userId));
                return "transaction_form";
            }

            // Lưu giao dịch
            Transaction transaction = new Transaction(0, userId, type, amount, date, categoryId, description, accountId);
            transactionService.saveTransaction(transaction, userId);

            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding transaction: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories(userId));
            model.addAttribute("accounts", accountService.getAllAccounts(userId));
            model.addAttribute("mode", "add");
            return "transaction_form";
        }
    }

	@GetMapping("/transaction/edit")
	public String showEditForm(@RequestParam("id") int id, Model model, HttpSession session) {
	    User user = (User) session.getAttribute("loggedInUser");
	    if (user == null) return "redirect:/login";
	    Transaction transaction = transactionService.getTransactionById(user.getUserId(), id);
	    model.addAttribute("transaction", transaction);
	    // Nếu có account/category dạng select cần truyền vào model
	    List<Account> accounts = accountService.getAllAccounts(user.getUserId());
	    List<Category> categories = categoryService.getAllCategories(user.getUserId());
	    model.addAttribute("accounts", accounts);
	    model.addAttribute("categories", categories);
	    model.addAttribute("mode", "edit");
	    return "transaction_form"; // Tạo file JSP này ở bước sau
	}
    
    @PostMapping("/transaction/update")
    public String updateTransaction(
            @RequestParam("id") int id,
            @RequestParam("type") String type,
            @RequestParam("amount") double amount,
            @RequestParam("date") String date,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("accountId") int accountId,
            @RequestParam(name = "description", required = false) String description,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        try {
            LocalDate localDate = LocalDate.parse(date);
            // Tạo một Transaction mới với dữ liệu từ form
            Transaction updatedTransaction = new Transaction(id, userId, type, amount, date, categoryId, description, accountId);
            transactionService.updateTransaction(userId, id, type, amount, localDate, categoryId, description, accountId);
            return "redirect:/"; //
        } catch (Exception e) {
            // Nếu có lỗi, sử dụng dữ liệu từ form để hiển thị lại
            model.addAttribute("error", e.getMessage());
            User currentUser = (User) session.getAttribute("loggedInUser");
            int currentUserId = currentUser.getUserId();
            model.addAttribute("categories", categoryService.getAllCategories(currentUserId));
            model.addAttribute("accounts", accountService.getAllAccounts(currentUserId));
            model.addAttribute("transaction", new Transaction(id, currentUserId, type, amount, date, categoryId, description, accountId)); // Sử dụng dữ liệu mới từ form
            return "/";
        }
    }

    @GetMapping("/transaction/delete")
    public String deleteTransaction(
            @RequestParam("id") int id,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            transactionService.deleteTransaction(userId, id);
            return "redirect:/";  
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting transaction: " + e.getMessage());
            return "redirect:/";
        }
    }

}

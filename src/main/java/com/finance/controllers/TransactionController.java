package com.finance.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//    @PostMapping("/transaction/add")
//    public String addTransaction(
//            @RequestParam("type") String type,
//            @RequestParam("amount") double amount,
//            @RequestParam("date") String date,
//            @RequestParam("categoryId") int categoryId,
//            @RequestParam("accountId") int accountId,
//            @RequestParam(name = "description" ,required = false) String description,
//            Model model,
//            HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("loggedInUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//            int userId = user.getUserId();
//            LocalDate localDate = LocalDate.parse(date);
//            transactionService.addTransaction(userId, type, amount, localDate, categoryId, description, accountId);
//            return "redirect:/transactions";
//        } catch (Exception e) {
//            model.addAttribute("error", "Error adding transaction: " + e.getMessage());
//            return "transaction_form";
//        }
//    }
	
//	@PostMapping("/transaction/add")
//    public String addTransaction(
//            @RequestParam("type") String type,
//            @RequestParam("amount") double amount,
//            @RequestParam("date") String date,
//            @RequestParam("categoryId") int categoryId,
//            @RequestParam("accountId") int accountId,
//            @RequestParam(name = "description", required = false) String description,
//            Model model,
//            HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("loggedInUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//            int userId = user.getUserId();
//            LocalDate localDate = LocalDate.parse(date);
//            transactionService.addTransaction(userId, type, amount, localDate, categoryId, description, accountId);
//            return "redirect:/transactions";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            User user = (User) session.getAttribute("loggedInUser");
//            int userId = user.getUserId();
//            model.addAttribute("categories", categoryService.getAllCategories(userId));
//            model.addAttribute("accounts", accountService.getAllAccounts(userId));
//            return "transaction_form";
//        }
//    }
	
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

            // Cập nhật số dư tài khoản
            accountService.updateBalance(accountId, userId, amount, type);

            return "redirect:/transactions";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding transaction: " + e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories(userId));
            model.addAttribute("accounts", accountService.getAllAccounts(userId));
            return "transaction_form";
        }
    }

    @GetMapping("/transaction/edit")
    public String showEditForm(
            @RequestParam("id") int id,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        Transaction transaction = transactionService.getTransactionById(userId, id);
        if (transaction == null) {
            model.addAttribute("error", "Transaction not found");
            return "redirect:/transactions";
        }
        model.addAttribute("categories", categoryService.getAllCategories(userId));
        model.addAttribute("accounts", accountService.getAllAccounts(userId));
        model.addAttribute("transaction", transaction);
        return "transaction_form";
    }

//    @PostMapping("/transaction/update")
//    public String updateTransaction(
//            @RequestParam("id") int id,
//            @RequestParam("type") String type,
//            @RequestParam("amount") double amount,
//            @RequestParam("date") String date,
//            @RequestParam("categoryId") int categoryId,
//            @RequestParam("accountId") int accountId,
//            @RequestParam(name = "description", required = false) String description,
//            Model model,
//            HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("loggedInUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//            int userId = user.getUserId();
//            LocalDate localDate = LocalDate.parse(date);
//            transactionService.updateTransaction(userId, id, type, amount, localDate, categoryId, description, accountId);
//            return "redirect:/transactions";
//        } catch (Exception e) {
//            model.addAttribute("error", "Error updating transaction: " + e.getMessage());
//            return "transaction_form";
//        }
//    }
    
//    @PostMapping("/transaction/update")
//    public String updateTransaction(
//            @RequestParam("id") int id,
//            @RequestParam("type") String type,
//            @RequestParam("amount") double amount,
//            @RequestParam("date") String date,
//            @RequestParam("categoryId") int categoryId,
//            @RequestParam("accountId") int accountId,
//            @RequestParam(name = "description", required = false) String description,
//            Model model,
//            HttpSession session) {
//        try {
//            User user = (User) session.getAttribute("loggedInUser");
//            if (user == null) {
//                return "redirect:/login";
//            }
//            int userId = user.getUserId();
//            LocalDate localDate = LocalDate.parse(date);
//            transactionService.updateTransaction(userId, id, type, amount, localDate, categoryId, description, accountId);
//            return "redirect:/transactions";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            User user = (User) session.getAttribute("loggedInUser");
//            int userId = user.getUserId();
//            model.addAttribute("categories", categoryService.getAllCategories(userId));
//            model.addAttribute("accounts", accountService.getAllAccounts(userId));
//            model.addAttribute("transaction", transactionService.getTransactionById(userId, id));
//            return "transaction_form";
//        }
//    }
    
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
            return "redirect:/transactions"; // Quay lại danh sách sau khi cập nhật thành công
        } catch (Exception e) {
            // Nếu có lỗi, sử dụng dữ liệu từ form để hiển thị lại
            model.addAttribute("error", e.getMessage());
            User currentUser = (User) session.getAttribute("loggedInUser");
            int currentUserId = currentUser.getUserId();
            model.addAttribute("categories", categoryService.getAllCategories(currentUserId));
            model.addAttribute("accounts", accountService.getAllAccounts(currentUserId));
            model.addAttribute("transaction", new Transaction(id, currentUserId, type, amount, date, categoryId, description, accountId)); // Sử dụng dữ liệu mới từ form
            return "transaction_form";
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
            return "redirect:/transactions";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting transaction: " + e.getMessage());
            return "redirect:/transactions";
        }
    }

    @GetMapping("/transactions")
    public String listTransactions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("transactions", transactionService.getAllTransactions(userId));
        return "transaction_list";
    }
}

package com.finance.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.dao.UserDAO;
import com.finance.entities.Account;
import com.finance.entities.Budget;
import com.finance.entities.Reminder;
import com.finance.entities.Transaction;
import com.finance.entities.User;
import com.finance.service.AccountService;
import com.finance.service.BudgetService;
import com.finance.service.ReminderService;
import com.finance.service.TransactionService;
import com.finance.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final BudgetService budgetService;
    private final ReminderService reminderService;
    private final UserService userService;

    @Autowired
    public HomeController(TransactionService transactionService, AccountService accountService,
                          BudgetService budgetService, ReminderService reminderService, UserService userService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.reminderService = reminderService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(name = "year" ,required = false) Integer year,
            @RequestParam(name = "month" ,required = false) Integer month,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();

        // Lấy tất cả giao dịch
        List<Transaction> allTransactions = transactionService.getAllTransactions(userId);

        // Lọc giao dịch theo năm và tháng nếu có
        List<Transaction> filteredTransactions = allTransactions;
        if (year != null || month != null) {
            filteredTransactions = allTransactions.stream()
                .filter(t -> (year == null || t.getDate().getYear() == year) &&
                             (month == null || t.getDate().getMonthValue() == month))
                .collect(Collectors.toList());
        }

        // Lấy danh sách ngân sách, tài khoản, và reminder
        List<Budget> budgets = budgetService.getAllBudgets(userId);
        List<Account> accounts = accountService.getAllAccounts(userId);
        List<Reminder> pendingReminders = reminderService.getPendingReminders(userId);

        // Thêm dữ liệu vào model
        model.addAttribute("username", user.getName());
        model.addAttribute("totalIncome", transactionService.getTotalIncome(userId));
        model.addAttribute("totalExpenses", transactionService.getTotalExpenses(userId));
        model.addAttribute("remainingBudget", budgetService.getRemainingBudget(userId));
        model.addAttribute("pendingReminders", pendingReminders);
        model.addAttribute("transactions", filteredTransactions);
        model.addAttribute("budgets", budgets);
        model.addAttribute("accounts", accounts);

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("error", model.containsAttribute("error") ? model.asMap().get("error") : null);
        return "login";
    }
    
    @PostMapping("/login")
    public String loginProcess(@RequestParam("username") String username, @RequestParam("password") String password,
                              Model model, HttpSession session) {
        User user = userService.getUserByUsername(username);
        if (user != null && userService.checkPassword(password, user.getPassword())) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("error", model.containsAttribute("error") ? model.asMap().get("error") : null);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("username") String username,
                          @RequestParam("password") String password, Model model) {
        try {
            userService.registerUser(name, username, password);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
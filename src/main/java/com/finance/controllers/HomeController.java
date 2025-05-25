package com.finance.controllers;

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
import com.finance.entities.User;
import com.finance.service.BudgetService;
import com.finance.service.ReminderService;
import com.finance.service.TransactionService;

@Controller
public class HomeController {

    private final UserDAO userDAO;
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final ReminderService reminderService;

    @Autowired
    public HomeController(UserDAO userDAO, TransactionService transactionService,
                          BudgetService budgetService, ReminderService reminderService) {
        this.userDAO = userDAO;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.reminderService = reminderService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String username = auth.getName();
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                model.addAttribute("username", user.getName());
                model.addAttribute("totalIncome", transactionService.getTotalIncome());
                model.addAttribute("totalExpenses", transactionService.getTotalExpenses());
                model.addAttribute("remainingBudget", transactionService.getRemainingBudget());
                model.addAttribute("pendingReminders", reminderService.getPendingReminders());
                return "index";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
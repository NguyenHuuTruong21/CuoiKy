package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Account;
import com.finance.entities.User;
import com.finance.service.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/accounts")
    public String listAccounts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("accounts", accountService.getAllAccounts(userId));
        return "account_list";
    }
	
	@GetMapping("/account/add")
    public String showAddForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", new Account());
        return "account_form";
    }
	
	@PostMapping("/account/add")
    public String addAccount(
            @RequestParam("name") String name,
            @RequestParam("balance") double balance,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            Account account = new Account(0, name, balance);
            account.setUserId(userId);
            accountService.saveAccount(account); // Giả định saveAccount không cần userId, nếu cần thì thêm
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding account: " + e.getMessage());
            return "account_form";
        }
    }

    @GetMapping("/account/delete")
    public String deleteAccount(@RequestParam("id") int id, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            accountService.deleteAccount(id); // Giả định deleteAccount không cần userId, nếu cần thì thêm
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting account: " + e.getMessage());
            return "redirect:/accounts";
        }
    }
}

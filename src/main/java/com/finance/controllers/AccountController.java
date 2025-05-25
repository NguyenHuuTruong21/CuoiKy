package com.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Account;
import com.finance.service.AccountService;

@Controller
public class AccountController {
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/accounts")
	public String listAccounts(Model model) {
		model.addAttribute("accounts", accountService.getAllAccounts());
		return "account_list";
	}
	
	@GetMapping("/account/add")
	public String showAddForm(Model model) {
		model.addAttribute("account", new Account());
		return "account_form";
	}
	
	@PostMapping("/account/add")
    public String addAccount(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam double balance,
            Model model) {
		try {
            Account account = new Account(0, name, balance);
            accountService.saveAccount(account);
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding account: " + e.getMessage());
            return "account_form";
        }
    }
	
	@GetMapping("/account/delete")
	public String deleteAccount(@RequestParam int id, Model model) {
		try {
            accountService.deleteAccount(id);
            return "redirect:/accounts";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting account: " + e.getMessage());
            return "redirect:/accounts";
        }
	}
}

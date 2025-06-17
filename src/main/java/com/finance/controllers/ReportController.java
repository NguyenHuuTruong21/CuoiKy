package com.finance.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.User;
import com.finance.service.TransactionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReportController {
	private final TransactionService transactionService;
	
	@Autowired
	public ReportController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@GetMapping("/reports")
    public String showReportForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        return "report_form";
    }

    @PostMapping("/reports/monthly")
    public String getMonthlyReport(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        Map<String, Double> report = transactionService.getMonthlyReport(userId, year, month);
        model.addAttribute("report", report);
        model.addAttribute("type", "Monthly");
        model.addAttribute("period", month + "/" + year);
        return "report_view";
    }

    @PostMapping("/reports/yearly")
    public String getYearReport(
            @RequestParam("year") int year,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        Map<String, Double> report = transactionService.getYearlyReport(userId, year);
        model.addAttribute("report", report);
        model.addAttribute("type", "Yearly");
        model.addAttribute("period", String.valueOf(year));
        return "report_view";
    }

    @GetMapping("/reports/expense-chart")
    public String showExpenseChart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        Map<String, Double> expenseByCategory = transactionService.getExpenseByCategory(userId);
        model.addAttribute("expenseData", expenseByCategory);
        return "expense_chart";
    }
}

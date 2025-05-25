package com.finance.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.service.TransactionService;

@Controller
public class ReportController {
	private final TransactionService transactionService;
	
	@Autowired
	public ReportController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@GetMapping("/reports")
	public String showReportForm(Model model) {
		return "report_form";
	}
	
	@PostMapping("/reports/monthly")
    public String getMonthlyReport(
            @RequestParam int year,
            @RequestParam int month,
            Model model) {
        Map<String, Double> report = transactionService.getMonthlyReport(year, month);
        model.addAttribute("report", report);
        model.addAttribute("type", "Monthly");
        model.addAttribute("period", month + "/" + year);
        return "report_view";
    }
	
	@PostMapping("/reports/yearly")
	public String getYearReport(
			@RequestParam int year,
			Model model) {
		Map<String, Double> report = transactionService.getYearlyReport(year);
		model.addAttribute("report", report);
        model.addAttribute("type", "Yearly");
        model.addAttribute("period", String.valueOf(year));
        return "report_view";
	}
	
	@GetMapping("/reports/expense-chart")
	public String showExpenseChart(Model model) {
		Map<String, Double> expenseByCategory = transactionService.getExpenseByCategory();
		model.addAttribute("expenseData", expenseByCategory);
		return "expense_chart";
	}
}

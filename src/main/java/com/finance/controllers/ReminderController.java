package com.finance.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Reminder;
import com.finance.service.ReminderService;

@Controller
public class ReminderController {
	private final ReminderService reminderService;
	
	@Autowired
	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}
	
	@GetMapping("/reminders")
	public String listReminders(Model model) {
		model.addAttribute("reminders", reminderService.getAllReminders());
        model.addAttribute("pendingReminders", reminderService.getPendingReminders());
        return "reminder_list";
	}
	
	@GetMapping("/reminder/add")
	public String showAddForm(Model model) {
		model.addAttribute("reminder", new Reminder());
		return "reminder_form";
	}
	
	@PostMapping("/reminder/add")
    public String addReminder(
            @RequestParam String billName,
            @RequestParam double amount,
            @RequestParam String dueDate,
            Model model) {
        try {
            LocalDate localDueDate = LocalDate.parse(dueDate);
            Reminder reminder = new Reminder(0, billName, amount, localDueDate);
            reminderService.saveReminder(reminder);
            return "redirect:/reminders";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding reminder: " + e.getMessage());
            return "reminder_form";
        }
    }
	
	@GetMapping("/reminder/markPaid")
    public String markAsPaid(
    		@RequestParam int id,
    		Model model) {
        try {
            reminderService.markAsPaid(id);
            return "redirect:/reminders";
        } catch (Exception e) {
            model.addAttribute("error", "Error marking reminder as paid: " + e.getMessage());
            return "redirect:/reminders";
        }
    }
	
	@GetMapping("/reminder/delete")
	public String deleteReminder(
			@RequestParam int id, 
			Model model) {
		try {
			reminderService.deleteReminder(id);
			return "redirect:/reminders";
		} catch (Exception e) {
			model.addAttribute("error", "Error deleting reminder: " + e.getMessage());
			return "redirect:/reminders";
		}
	}
}

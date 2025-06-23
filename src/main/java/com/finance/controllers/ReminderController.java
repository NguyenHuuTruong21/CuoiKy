package com.finance.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.entities.Reminder;
import com.finance.entities.User;
import com.finance.service.ReminderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReminderController {
	private final ReminderService reminderService;
	
	@Autowired
	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}
	
	@GetMapping("/reminders")
    public String listReminders(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        int userId = user.getUserId();
        model.addAttribute("reminders", reminderService.getAllReminders(userId));
        model.addAttribute("pendingReminders", reminderService.getPendingReminders(userId));
        return "reminder_list";
    }

    @GetMapping("/reminder/add")
    public String showAddForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("reminder", new Reminder());
        return "reminder_form";
    }

    @PostMapping("/reminder/add")
    public String addReminder(
            @RequestParam("billName") String billName,
            @RequestParam("amount") double amount,
            @RequestParam("dueDate") String dueDate,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            LocalDate localDueDate = LocalDate.parse(dueDate);
            Reminder reminder = new Reminder(0, billName, amount, localDueDate, false, false, userId); 
            reminderService.saveReminder(reminder);
            return "redirect:/reminders";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding reminder: " + e.getMessage());
            return "reminder_form";
        }
    }

    @GetMapping("/reminder/markPaid")
    public String markAsPaid(
            @RequestParam("id") int id,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            reminderService.markAsPaid(id, userId);
            return "redirect:/reminders";
        } catch (Exception e) {
            model.addAttribute("error", "Error marking reminder as paid: " + e.getMessage());
            return "reminder_list";
        }
    }
    
    @GetMapping("/reminder/delete")
    public String deleteReminder(
            @RequestParam("id") int id,
            Model model,
            HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return "redirect:/login";
            }
            int userId = user.getUserId();
            reminderService.deleteReminder(id, userId);
            return "redirect:/reminders";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting reminder: " + e.getMessage());
            return "reminder_list";
        }
    }
}

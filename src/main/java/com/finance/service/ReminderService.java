package com.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.ReminderDAO;
import com.finance.entities.Reminder;

@Service
public class ReminderService {
	private final ReminderDAO reminderDAO;
	
	@Autowired
	public ReminderService(ReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }
	
	public int saveReminder(Reminder reminder) {
        if (reminder == null || reminder.getBillName() == null || reminder.getDueDate() == null) {
            throw new IllegalArgumentException("Reminder bill name and due date must not be null");
        }
        return reminderDAO.saveReminder(reminder);
    }
	
	public void updateReminder(Reminder reminder, int userId) {
	    reminderDAO.updateReminder(reminder); 
	}
	
	public Reminder getReminderById(int id, int userid) {
        return reminderDAO.getReminderById(id, userid);
    }

	public List<Reminder> getAllReminders(int userId) {
        return reminderDAO.getAllReminders(userId);
    }

	public List<Reminder> getPendingReminders(int userId) {
	    LocalDate today = LocalDate.now();
	    return getAllReminders(userId).stream()
	        .filter(r -> !r.isPaid() && !r.getDueDate().isBefore(today))
	        .collect(Collectors.toList());
	}

	public void markAsPaid(int id, int userId) {
        Reminder reminder = getReminderById(id, userId);
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder not found or you do not have permission to update this reminder");
        }
        reminder.setPaid(true);
        reminderDAO.updateReminder(reminder);
    }
	
	public List<Reminder> getAchievedReminders(int userId, double totalBalance) {
	    List<Reminder> reminders = getAllReminders(userId);
	    LocalDate today = LocalDate.now();
	    return reminders.stream()
	        .filter(r -> !r.isNotified()) 
	        .filter(r -> !r.isPaid())     
	        .filter(r -> (r.getDueDate() != null && !today.isBefore(r.getDueDate())))
	        .filter(r -> totalBalance >= r.getAmount())
	        .collect(Collectors.toList());
	}

	public void markNotified(int reminderId, int userId) {
	    Reminder reminder = getReminderById(reminderId, userId);
	    if (reminder != null) {
	        reminder.setNotified(true);
	        updateReminder(reminder, userId); 
	    }
	}

	public void deleteReminder(int id, int userId) {
        Reminder reminder = getReminderById(id, userId);
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder not found or you do not have permission to delete this reminder");
        }
        reminderDAO.deleteReminder(id, userId);
    }
}

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
	
	public Reminder getReminderById(int id, int userid) {
        return reminderDAO.getReminderById(id, userid);
    }

	public List<Reminder> getAllReminders(int userId) {
        return reminderDAO.getAllReminders(userId);
    }
	
//    public List<Reminder> getPendingReminders(int userId) {
//        return getAllReminders(userId).stream()
//                .filter(r -> !r.isPaid() && r.getDueDate().isAfter(LocalDate.now().minusDays(1)))
//                .collect(Collectors.toList());
//    }
	
	public List<Reminder> getPendingReminders(int userId) {
        LocalDate today = LocalDate.now(); // 2025-06-02
        return getAllReminders(userId).stream()
                .filter(r -> !r.isPaid() && !r.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

//    public void markAsPaid(int id) {
//        Reminder reminder = getReminderById(id);
//        if (reminder == null) {
//            throw new IllegalArgumentException("Reminder not found");
//        }
//        reminder.setPaid(true);
//        reminderDAO.saveReminder(reminder);
//    }
	
	public void markAsPaid(int id, int userId) {
        Reminder reminder = getReminderById(id, userId);
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder not found or you do not have permission to update this reminder");
        }
        reminder.setPaid(true);
        reminderDAO.updateReminder(reminder);
    }

//    public void deleteReminder(int id) {
//        reminderDAO.deleteReminder(id);
//    }
	
	public void deleteReminder(int id, int userId) {
        Reminder reminder = getReminderById(id, userId);
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder not found or you do not have permission to delete this reminder");
        }
        reminderDAO.deleteReminder(id, userId);
    }
}

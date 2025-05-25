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
	
	public Reminder getReminderById(int id) {
        return reminderDAO.getReminderById(id);
    }

    public List<Reminder> getAllReminders() {
        return reminderDAO.getAllReminders();
    }
	
    public List<Reminder> getPendingReminders() {
        return reminderDAO.getAllReminders().stream()
                .filter(r -> !r.isPaid() && r.getDueDate().isAfter(LocalDate.now().minusDays(1)))
                .collect(Collectors.toList());
    }

    public void markAsPaid(int id) {
        Reminder reminder = getReminderById(id);
        if (reminder == null) {
            throw new IllegalArgumentException("Reminder not found");
        }
        reminder.setPaid(true);
        reminderDAO.saveReminder(reminder);
    }

    public void deleteReminder(int id) {
        reminderDAO.deleteReminder(id);
    }
}

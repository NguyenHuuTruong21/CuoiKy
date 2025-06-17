package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.BudgetDAO;
import com.finance.entities.Budget;
import com.finance.entities.Status;

@Service
public class BudgetService {
	private final BudgetDAO budgetDAO;
	
	@Autowired
	public BudgetService(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}
	
	public int saveBudget(Budget budget, int userId) {
        if (budget == null || budget.getCategory() == null) {
            throw new IllegalArgumentException("Budget category must not be null");
        }
        budget.setUserId(userId); // Đặt userId trước khi lưu
        return budgetDAO.saveBudget(budget, userId);
    }
	
	public Budget getBudgetById(int id, int userId) {
        return budgetDAO.getBudgetById(id, userId);
    }

    public Budget getBudgetByCategoryId(int categoryId, int userId) {
        return budgetDAO.getBudgetByCategoryId(categoryId, userId);
    }
	
    public List<Budget> getAllBudgets(int userId) {
        return budgetDAO.getAllBudgets(userId);
    }
	
//	public void deleteBudget(int id, int userId) {
//        budgetDAO.deleteBudget(id, userId);
//    }
    
    public void deleteBudget(int id, int userId) {
        Budget budget = getBudgetById(id, userId);
        if (budget == null) {
            throw new IllegalArgumentException("Budget not found or you do not have permission to delete this budget");
        }
        budgetDAO.deleteBudget(id, userId);
    }
	
//	public double getRemainingBudget(int userId) {
//        return getAllBudgets(userId).stream()
//                .mapToDouble(b -> b.getAmount() - b.getSpent())
//                .sum();
//    }
    public double getRemainingBudget(int userId) {
        return getAllBudgets(userId).stream()
                .mapToDouble(Budget::getAmount)
                .sum();
    }
    
 // Cập nhật trạng thái dựa trên số tiền tích lũy
    public void updateBudgetStatus(Budget budget, double accumulatedAmount) {
        double targetAmount = budget.getAmount();
        if (accumulatedAmount >= targetAmount) {
            budget.setStatus(Status.Done);
        } else if (accumulatedAmount >= targetAmount / 2) {
            budget.setStatus(Status.InProgress);
        } else {
            budget.setStatus(Status.ToDo);
        }
        budgetDAO.saveBudget(budget, budget.getUserId());
    }
}

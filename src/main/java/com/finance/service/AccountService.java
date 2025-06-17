package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.AccountDAO;
import com.finance.entities.Account;

@Service
public class AccountService {
	private final AccountDAO accountDAO;
	
	@Autowired
	public AccountService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	public int saveAccount(Account account) {
        if (account == null || account.getName() == null) {
            throw new IllegalArgumentException("Account name must not be null");
        }
        return accountDAO.saveAccount(account);
    }
	
//    public Account getAccountById(int id) {
//        return accountDAO.getAccountById(id);
//    }
	
	public Account getAccountById(int id, int userId) {
        Account account = accountDAO.getAccountById(id, userId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found or you do not have permission to access this account");
        }
        return account;
    }
	
    public List<Account> getAllAccounts(int userId) {
        return accountDAO.getAllAccounts(userId);
    }
	
    public void deleteAccount(int id) {
        accountDAO.deleteAccount(id);
    }
	
//    public void updateBalance(int id, double amount, String type) {
//        Account account = getAccountById(id);
//        if (account == null) {
//            throw new IllegalArgumentException("Account not found");
//        }
//        if ("income".equalsIgnoreCase(type)) {
//            account.setBalance(account.getBalance() + amount);
//        } else if ("expense".equalsIgnoreCase(type)) {
//            account.setBalance(account.getBalance() - amount);
//        }
//        accountDAO.saveAccount(account);
//    }
    
    public double getAccountBalance(int accountId, int userId) {
        Account account = getAccountById(accountId, userId);
        return account.getBalance();
    }
    
    public void updateBalance(int accountId, int userId, double amount, String type) {
        Account account = getAccountById(accountId, userId);
        if ("income".equalsIgnoreCase(type)) {
            account.setBalance(account.getBalance() + amount);
        } else if ("expense".equalsIgnoreCase(type)) {
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance in account");
            }
            account.setBalance(account.getBalance() - amount);
        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + type);
        }
        accountDAO.updateAccount(account);
    }
}

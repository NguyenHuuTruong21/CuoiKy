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
	
	public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
	
	public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
	
	public void deleteAccount(int id) {
        accountDAO.deleteAccount(id);
    }
	
	public void updateBalance(int id, double amount, String type) {
        Account account = getAccountById(id);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        if ("income".equalsIgnoreCase(type)) {
            account.setBalance(account.getBalance() + amount);
        } else if ("expense".equalsIgnoreCase(type)) {
            account.setBalance(account.getBalance() - amount);
        }
        accountDAO.saveAccount(account);
    }
}

package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.finance.dao.UserDAO;
import com.finance.entities.User;

@Service
public class UserService {
	private final UserDAO userDAO;
	
	@Autowired
	public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
	
	public int registerUser(String name, String username, String password) {
        if (name == null || username == null || password == null) {
            throw new IllegalArgumentException("Name, username, and password must not be null");
        }
        if (userDAO.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User(0, name, username, password, "ROLE_USER");
        return userDAO.saveUser(user);
    }
	
	public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}

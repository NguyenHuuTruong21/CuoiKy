package com.finance.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finance.dao.UserDAO;
import com.finance.entities.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private UserDAO userDAO;
	
	@Autowired
	public CustomUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.getUserByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
		return new org.springframework.security.core.userdetails.User(
	            String.valueOf(user.getUserId()), 
	            // Chuyển userId thành String để phù hợp với Spring Security
//				user.getUsername(),
	            user.getPassword(),
	            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
	        );
	}
}

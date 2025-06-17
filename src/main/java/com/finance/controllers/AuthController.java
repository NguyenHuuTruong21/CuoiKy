//package com.finance.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.finance.dao.UserDAO;
//import com.finance.entities.User;
//
//@Controller
//public class AuthController {
//
//    private final UserDAO userDAO;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    public AuthController(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
//        this.userDAO = userDAO;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String registerPage() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String register(
//            @RequestParam("name") String name,
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            Model model) {
//        try {
//            if (userDAO.getUserByUsername(username) != null) {
//                model.addAttribute("error", "Username already exists");
//                return "register";
//            }
//            User user = new User(0, name, username, passwordEncoder.encode(password), "ROLE_USER");
//            userDAO.saveUser(user);
//            return "redirect:/login?success";
//        } catch (Exception e) {
//            model.addAttribute("error", "Registration failed: " + e.getMessage());
//            return "register";
//        }
//    }
//}

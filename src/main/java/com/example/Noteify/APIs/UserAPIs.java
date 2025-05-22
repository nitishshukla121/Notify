package com.example.Noteify.APIs;

import com.example.Noteify.Model.User;
import com.example.Noteify.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAPIs {

    @Autowired
    private UserService userService;

    // Register new user
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        User saved = userService.saveUser(user);
        if (saved != null) {
            return "User registered successfully!";
        } else {
            return "Registration failed.";
        }
    }

    // Login user
    @PostMapping("/login")
    public String login(@RequestBody User loginUser, HttpSession session) {
        User user = userService.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword());
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }

    // Logout user
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully!";
    }

    // Get currently logged-in user based on session
    @GetMapping("/me")
    public User currentUser(HttpSession session) {
        User currentUser = userService.getCurrentUserFromSession(session);
        if (currentUser != null) {
            return currentUser;
        } else {
            throw new RuntimeException("No user logged in.");
        }
    }
}

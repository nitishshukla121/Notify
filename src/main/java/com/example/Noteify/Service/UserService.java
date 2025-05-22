package com.example.Noteify.Service;

import com.example.Noteify.Model.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

    User saveUser(User user);

    void deleteUser(int id);

    User findByEmailAndPassword(String email, String password);
    
    User getCurrentUserFromSession(HttpSession session);
}

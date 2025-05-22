package com.example.Noteify.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Noteify.Model.User;
import com.example.Noteify.Repo.UserRepo;
import com.example.Noteify.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }
    @Override
    public User getCurrentUserFromSession(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");  
    }
}

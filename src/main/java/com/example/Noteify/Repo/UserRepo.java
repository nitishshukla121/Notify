package com.example.Noteify.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Noteify.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    
    User findByEmailAndPassword(String email, String password);
}

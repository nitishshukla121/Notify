package com.example.Noteify.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Model.User;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Long> {
    List<Notes> findByUser(User user);
    Optional<Notes> findByIdAndUser(Long id, User user);
}

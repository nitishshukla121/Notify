package com.example.Noteify.Repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Noteify.Model.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {

    List<Task> findByDuedateBetween(Date start, Date end);
    

    List<Task> findByUser_Id(int userId);  // Find tasks by user id
}

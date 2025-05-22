package com.example.Noteify.Service;

import java.sql.Date;
import java.util.List;

import com.example.Noteify.Model.Task;
import com.example.Noteify.Model.User;

public interface TaskService {

    Task saveTask(Task task);  // Create a task

    List<Task> getAllTasks();  // Get all tasks

    Task getTaskById(int id);  // Get task by ID

    Task updateTask(int id, Task task);  // Update task
    void deleteTaskByUser(int id, User user);

    String deleteTask(int id);  // Delete task
    List<Task> getTasksSortedByPriorityForUser(User user);
    List<Task> getTasksSortedByStatusForUser(User user);
    List<Task> getUpcomingTasksForUser(User user);


    List<Task> findByDuedateBetween(Date start, Date end);  // Get tasks within a date range

    List<Task> getTasksSortedByPriority();  // Get tasks sorted by priority

    List<Task> getTasksSortedByStatus();  // Get tasks sorted by status

    List<Task> getUpcomingTasksSortedByDueDate();  // Get tasks with upcoming due dates

    List<Task> getTasksByCategory(String category);  // Get tasks by category

    List<Task> getTasksForUser(User user);  // Get tasks for a specific user (logged-in user)
}

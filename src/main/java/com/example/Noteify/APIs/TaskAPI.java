package com.example.Noteify.APIs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Noteify.Model.Task;
import com.example.Noteify.Model.User;
import com.example.Noteify.Service.TaskService;
import com.example.Noteify.Exception.UnauthorizedException;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/task")
public class TaskAPI {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/upcoming")
    public List<Task> getUpcomingTasks() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return taskService.findByDuedateBetween(java.sql.Date.valueOf(today), java.sql.Date.valueOf(nextWeek));
    }

    @GetMapping("/priority")
    public List<Task> getTasksSortedByPriority() {
        return taskService.getTasksSortedByPriority();
    }

    @GetMapping("/status")
    public List<Task> getTasksSortedByStatus() {
        return taskService.getTasksSortedByStatus();
    }

    @GetMapping("/upcoming/priority")
    public List<Task> getUpcomingTasksSortedByDate() {
        return taskService.getUpcomingTasksSortedByDueDate();
    }

    @GetMapping("/category")
    public List<Task> getTasksByCategory(@RequestParam String category) {
        return taskService.getTasksByCategory(category);
    }

    @GetMapping("/user")
    public List<Task> getTasksForUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser != null) {
            return taskService.getTasksForUser(currentUser);
        } else {
            throw new UnauthorizedException("User is not logged in");
        }
    }
}

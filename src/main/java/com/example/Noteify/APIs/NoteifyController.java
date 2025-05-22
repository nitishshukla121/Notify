package com.example.Noteify.APIs;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Model.Task;
import com.example.Noteify.Model.User;
import com.example.Noteify.Service.NoteService;
import com.example.Noteify.Service.TaskService;
import com.example.Noteify.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteifyController {

    private final UserService userService;
    private final TaskService taskService;
    private final NoteService noteService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User logged = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (logged != null) {
            session.setAttribute("user", logged);
            return "redirect:/dashboard/tasks";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    //——— TASK VIEWS ———

    @GetMapping("/dashboard/tasks")
    public String tasksPage(Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        List<Task> tasks = taskService.getTasksForUser(current);
        model.addAttribute("pageTitle", "Tasks");
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", tasks);
        return "userhome";
    }

    @PostMapping("/dashboard/tasks/add")
    public String addTask(@ModelAttribute Task task, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        task.setUser(current);
        task.setStatus("Pending");
        task.setCreatedAt(new Date());
        taskService.saveTask(task);
        return "redirect:/dashboard/tasks";
    }

    @PostMapping("/dashboard/tasks/complete/{id}")
    public String deleteTask(@PathVariable int id, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        taskService.deleteTaskByUser(id, current);
        return "redirect:/dashboard/tasks";
    }


    @PostMapping("/dashboard/tasks/update/{id}")
    public String updateTask(@ModelAttribute Task task,
                             @PathVariable int id,
                             HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        task.setId(id);
        task.setUser(current);
        taskService.updateTask(id, task);
        return "redirect:/dashboard/tasks";
    }

    @GetMapping("/dashboard/tasks/priority")
    public String sortedByPriority(Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        model.addAttribute("pageTitle", "Tasks");
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskService.getTasksSortedByPriorityForUser(current));
        return "userhome";
    }

    @GetMapping("/dashboard/tasks/status")
    public String sortedByStatus(Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        model.addAttribute("pageTitle", "Tasks");
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskService.getTasksSortedByStatusForUser(current));
        return "userhome";
    }

    @GetMapping("/dashboard/tasks/upcoming")
    public String upcomingTasks(Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        model.addAttribute("pageTitle", "Tasks");
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskService.getUpcomingTasksForUser(current));
        return "userhome";
    }

    //——— NOTES VIEWS ———

    @GetMapping("/dashboard/notes")
    public String notesPage(Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        List<Notes> notes = noteService.getAllNotes(current);
        model.addAttribute("pageTitle", "Notes");
        model.addAttribute("note", new Notes());
        model.addAttribute("notes", notes);
        return "userhome";
    }
 // Show the Edit Note form page
    @GetMapping("/dashboard/notes/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        Notes note = noteService.getByIdAndUser(id, current);
        if (note == null) {
            // Note not found or doesn't belong to user
            return "redirect:/dashboard/notes";
        }

        model.addAttribute("note", note);
        model.addAttribute("pageTitle", "Edit Note");
        return "editnote";  // name of the Thymeleaf template
    }
 // Handle form submission to update note
    @PostMapping("/dashboard/notes/update/{id}")
    public String updateNote(@PathVariable Long id, @ModelAttribute Notes note, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        note.setId(id);
        note.setUser(current);
        noteService.updateNoteContent(id, note, current);
        return "redirect:/dashboard/notes";
    }

    @PostMapping("/dashboard/notes/add")
    public String addNote(@ModelAttribute Notes note, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        note.setUser(current);
        note.setCreatedate(new Date());
        noteService.saveNotes(note, current);
        return "redirect:/dashboard/notes";
    }
    @PostMapping("/dashboard/notes/delete/{id}")
    public String deleteNote(@PathVariable Long id, HttpSession session) {
        User current = (User) session.getAttribute("user");
        if (current == null) return "redirect:/login";

        noteService.deleteNoteById(id, current);
        return "redirect:/dashboard/notes";
    }

}

package com.example.Noteify.APIs;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Model.User;
import com.example.Noteify.Service.NoteService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NotesAPIs {

    @Autowired
    private NoteService noteService;

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @PostMapping
    public ResponseEntity<Notes> saveNote(@RequestBody Notes note, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) return ResponseEntity.status(401).build();

        Notes saved = noteService.saveNotes(note, user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Notes>> getAllNotes(HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(noteService.getAllNotes(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNote(@PathVariable Long id, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) return ResponseEntity.status(401).build();

        Notes note = noteService.getByIdAndUser(id, user);
        return (note != null) ? ResponseEntity.ok(note) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable Long id, @RequestBody Notes note, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) return ResponseEntity.status(401).build();

        Notes updated = noteService.updateNoteContent(id, note, user);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null) return ResponseEntity.status(401).build();

        boolean deleted = noteService.deleteNoteById(id, user);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

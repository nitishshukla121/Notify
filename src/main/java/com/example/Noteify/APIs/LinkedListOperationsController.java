package com.example.Noteify.APIs;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/linkedlist/notes")
public class LinkedListOperationsController {

  /*  @Autowired
    private NoteService noteService;

    @PostMapping("/head")
    public ResponseEntity<Notes> addNoteAsHead(@RequestBody Notes note) {
        Notes newHead = noteService.addNoteAsHead(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHead);
    }

    @PostMapping("/after/{previousNoteId}")
    public ResponseEntity<Notes> addNoteAfter(@PathVariable Long previousNoteId, @RequestBody Notes newNote) {
        try {
            Notes addedNote = noteService.addNoteAfter(previousNoteId, newNote);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedNote);
        } catch (RuntimeException e) { // Catch specific exceptions like NotFound from service
            // Log the exception e.getMessage()
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/end")
    public ResponseEntity<Notes> addNoteAtEnd(@RequestBody Notes newNote) {
        try {
            Notes addedNote = noteService.addNoteAtEnd(newNote);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedNote);
        } catch (IllegalStateException e) { // Catch cycle detection
             // Log the exception e.getMessage()
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Or another appropriate error
        }
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<Notes>> getAllNotesInOrder() {
        List<Notes> orderedNotes = noteService.getAllNotesInOrder();
        return ResponseEntity.ok(orderedNotes);
    }

    @GetMapping("/ordered/titles")
    public ResponseEntity<List<String>> getAllNoteTitlesInOrder() {
        List<String> titles = noteService.getAllNotesInOrder().stream()
                                        .map(Notes::getTitle)
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(titles);
    }

    @DeleteMapping("/remove/{noteId}")
    public ResponseEntity<Void> removeNoteAndRelink(@PathVariable Long noteId) {
        try {
            noteService.removeNoteAndRelink(noteId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { // Catch specific exceptions like NotFound from service
            // Log the exception e.getMessage()
            return ResponseEntity.notFound().build();
        }
    }*/

    
}
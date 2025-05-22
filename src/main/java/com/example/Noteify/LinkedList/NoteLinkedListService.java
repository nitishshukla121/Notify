package com.example.Noteify.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Repo.NotesRepo;

@Service
public class NoteLinkedListService {
    private NoteNode head;
    
    @Autowired
    private NotesRepo notesRepo;

    public void insertAtEnd(Notes note) {
        notesRepo.save(note); // Save to DB
        
        NoteNode newNode = new NoteNode(note);
        if (head == null) {
            head = newNode;
        } else {
            NoteNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void deleteNoteById(Long id) {
        notesRepo.deleteById(id); // Delete from DB

        if (head == null) return;
        if (head.note.getId().equals(id)) {
            head = head.next;
            return;
        }

        NoteNode current = head;
        while (current.next != null && !current.next.note.getId().equals(id)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }
}
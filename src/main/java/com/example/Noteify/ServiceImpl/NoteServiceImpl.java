package com.example.Noteify.ServiceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Model.User;
import com.example.Noteify.Repo.NotesRepo;
import com.example.Noteify.Service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NotesRepo notesRepo;

    @Override
    public Notes saveNotes(Notes note, User user) {
        note.setUser(user);
        note.setCreatedate(new Date());
        note.setUpdatedate(new Date());
        return notesRepo.save(note);
    }

    @Override
    public List<Notes> getAllNotes(User user) {
        return notesRepo.findByUser(user);
    }

    @Override
    public Notes getByIdAndUser(Long id, User user) {
        return notesRepo.findByIdAndUser(id, user).orElse(null);
    }

    @Override
    @Transactional
    public Notes updateNoteContent(Long id, Notes updatedNote, User user) {
        Notes existingNote = getByIdAndUser(id, user);
        if (existingNote != null) {
            if (updatedNote.getTitle() != null)
                existingNote.setTitle(updatedNote.getTitle());
            if (updatedNote.getContent() != null)
                existingNote.setContent(updatedNote.getContent());

            existingNote.setPinned(updatedNote.isPinned());
            existingNote.setCategory(updatedNote.getCategory());
            existingNote.setUpdatedate(new Date());

            return notesRepo.save(existingNote);
        }
        return null;
    }

    @Override
    public boolean deleteNoteById(Long id, User user) {
        Notes note = getByIdAndUser(id, user);
        if (note != null) {
            notesRepo.delete(note);
            return true;
        }
        return false;
    }
}

package com.example.Noteify.Service;

import java.util.List;

import com.example.Noteify.Model.Notes;
import com.example.Noteify.Model.User;

public interface NoteService {

    Notes saveNotes(Notes note, User user);

    List<Notes> getAllNotes(User user);

    Notes getByIdAndUser(Long id, User user);

    Notes updateNoteContent(Long id, Notes updatedNote, User user);

    boolean deleteNoteById(Long id, User user);
}

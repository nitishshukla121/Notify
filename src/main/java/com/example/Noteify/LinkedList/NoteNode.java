package com.example.Noteify.LinkedList;
import com.example.Noteify.Model.Notes;

public class NoteNode {
	public Notes note;
    public NoteNode next;

    public NoteNode(Notes note) {
        this.note = note;
        this.next = null;
    }
}

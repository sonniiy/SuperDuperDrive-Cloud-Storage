package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void addNote(NoteForm noteForm) {

        Note note = new Note(null, noteForm.getNotetitle(), noteForm.getNotedescription(), noteForm.getUserid());
        notesMapper.addNote(note);

        System.out.println("Note was added to Database: " + note.getNoteid());
    }

    public List<Note> getNotes(int userId) {
        return notesMapper.getAllNotes(userId);

    }

    public void deleteNote(int fileId) {
        notesMapper.delete(fileId);
    }


    public void updateNote(NoteForm noteForm, int userId) {

        Note note = new Note(Integer.parseInt(noteForm.getNoteid()), noteForm.getNotetitle(), noteForm.getNotedescription(),
                noteForm.getUserid());

        notesMapper.update(note);

    }

}

package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

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

}

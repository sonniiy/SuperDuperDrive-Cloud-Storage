package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private UserService userService;
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;

    public NoteController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String handleNoteUpload(Authentication authentication, @ModelAttribute("noteForm") NoteForm noteForm, Model model) {

        int userId = getUserId(authentication);

        if (noteForm.getNoteid() == "") {
            noteForm.setUserid(userId);
            noteService.addNote(noteForm);
        } else {
            noteService.updateNote(noteForm, userId);
        }


        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));

        return "home";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Model model, Authentication authentication) {
        noteService.deleteNote(id);

        int userId = getUserId(authentication);

        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));

        return "home";
    }

    @GetMapping("edit/{id}")


    private int getUserId(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.getUser(authentication.getName()).getUserid();
        return userId;
    }

}

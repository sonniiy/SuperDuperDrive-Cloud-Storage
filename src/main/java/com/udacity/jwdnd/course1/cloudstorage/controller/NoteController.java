package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView handleNoteUpload(Authentication authentication, @ModelAttribute("noteForm") NoteForm noteForm, ModelMap model) {

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
        model.addAttribute("success", true);
        model.addAttribute("activeTeb", "notes");

        return new ModelAndView("forward:/result", model);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteNote(@PathVariable("id") int id, ModelMap model, Authentication authentication) {
        noteService.deleteNote(id);

        int userId = getUserId(authentication);

        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));
        model.addAttribute("success", true);

        return new ModelAndView("forward:/result", model);
    }


    private int getUserId(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.getUser(authentication.getName()).getUserid();
        return userId;
    }

}

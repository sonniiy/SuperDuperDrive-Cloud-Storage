package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService,
                          CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @ModelAttribute("noteForm")
    public NoteForm getNoteForm() {
        return new NoteForm();
    }

    @ModelAttribute("credentialForm")
    public CredentialForm getCredentialForm() { return new CredentialForm();
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService() {
        return new EncryptionService();
    }


    @GetMapping
    public  String getUploadPage(Authentication authentication, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserid();
        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));
        return "home";
    }


}

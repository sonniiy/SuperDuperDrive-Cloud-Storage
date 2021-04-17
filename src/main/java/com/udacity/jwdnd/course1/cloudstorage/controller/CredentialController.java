package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private CredentialService credentialService;
    private NoteService noteService;
    private FileService fileService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, NoteService noteService, FileService fileService, UserService userService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public  String handleCredentialUpload(Authentication authentication,
                                          @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model) {
        int userId = getUserId(authentication);

        if (credentialForm.getCredentialid() == "") {
            credentialForm.setUserid(userId + "");
            credentialService.addCredential(credentialForm);
        } else {
            return "home";
        }

        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));

        return "home";
    }

    private int getUserId(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.getUser(authentication.getName()).getUserid();
        return userId;
    }



}

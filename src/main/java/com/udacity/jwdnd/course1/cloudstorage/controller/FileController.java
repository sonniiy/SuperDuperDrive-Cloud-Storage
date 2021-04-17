package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;

    public FileController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String handleFileUpload(Authentication authentication, @ModelAttribute("file") File file,
                                   @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        int userID = getUserId(authentication);

        try {
            file = createFile(fileUpload, model, authentication);
            fileService.addFile(file,userID);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error not known");
            return "result";
        } catch (FilewithNameExists filewithNameExists) {
            model.addAttribute("errorMessage", "File with this name already exists");
            model.addAttribute("failed", "File with this name already exists");
            return "result";
        }

        int userId = userService.getUser(authentication.getName()).getUserid();

        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));
        return "home";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") int id, Model model, Authentication authentication) {
        fileService.deleteFile(id);

        int userId = getUserId(authentication);

        model.addAttribute("notes", this.noteService.getNotes(userId));
        model.addAttribute("files", this.fileService.getFiles(userId));
        model.addAttribute("credentials", this.credentialService.getCredentials(userId));

        return "home";
    }

    @GetMapping("/viewFiles/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("id") int id, Model model, Authentication authentication) {
        int userId = getUserId(authentication);
        byte[] fileContent = fileService.getFile(id).getFiledata();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(fileService.getFile(id).getContenttype()));
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContent, httpHeaders, HttpStatus.OK);

        return response;
    }

    private File createFile(MultipartFile fileUpload, Model model, Authentication authentication) throws IOException {
        byte[] fileContent = null;
        File file = null;

        if (fileUpload.isEmpty()) {
            model.addAttribute("errorMessage", "File is empty!");
            System.out.println("File is empty");
        }
        else {
            int userId = getUserId(authentication);
            // Set file parameters
            file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    ""+fileUpload.getSize(), userId, fileUpload.getBytes());

        }
        return file;
    }

    private int getUserId(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.getUser(authentication.getName()).getUserid();
        return userId;
    }

}

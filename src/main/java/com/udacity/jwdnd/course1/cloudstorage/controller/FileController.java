package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilewithNameExists;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model) {

        File file = null;
        try {
            file = createFile(fileUpload, model);
            fileService.addFile(file);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error not known");
        } catch (FilewithNameExists filewithNameExists) {
            model.addAttribute("errorMessage", "File with this name already exists");
            model.addAttribute("failed", "File with this name already exists");
        }

        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") int id, Model model) {
        fileService.deleteFile(id);
        model.addAttribute("files", this.fileService.getFiles());

        return "home";
    }

    @GetMapping("/viewFiles/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("id") int id, Model model) {
        byte[] fileContent = fileService.getFile(id).getFiledata();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(fileService.getFile(id).getContenttype()));
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContent, httpHeaders, HttpStatus.OK);

        return response;
    }

    private File createFile(MultipartFile fileUpload, Model model) throws IOException {
        byte[] fileContent = null;
        File file = null;

        if (fileUpload.isEmpty()) {
            model.addAttribute("errorMessage", "File is empty!");
            System.out.println("File is empty");
        }
        else {
            // Set file parameters
            file = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), "" +fileUpload.getSize(),
            1, fileUpload.getBytes());
        }
        return file;
    }

}

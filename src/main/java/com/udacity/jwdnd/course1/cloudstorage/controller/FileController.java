package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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

        byte[] fileContent = null;

        // Create file object and add properties
        File file = new File();
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setFilesize("" + fileUpload.getSize());
        //TODO Replace with correct userid
        Random rand = new Random();
        int upperbound = 100;
        int id = rand.nextInt();
        file.setUserid(id);

        // Save uploaded file in file object
        // Transform byte[] to Byte[]
        try {
            file.setFiledata(fileUpload.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileService.addFile(file);
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

}

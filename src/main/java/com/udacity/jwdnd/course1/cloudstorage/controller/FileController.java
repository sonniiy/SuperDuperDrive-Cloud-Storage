package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        file.setUserid(1);

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

}

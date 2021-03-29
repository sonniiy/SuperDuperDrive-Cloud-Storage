package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileService {

    private ArrayList<File> files;

    @PostConstruct
    public void postConstruct() {
        files = new ArrayList<>();
        System.out.println("Created FileService Bean");
    }

    public void addFile(File file) {
        files.add(file);
        System.out.println("File was added to Database: " + file.getFilename());
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void deleteFile(int id) {

        for(File file : files) {
            if(file.getFileId() == id) {
                files.remove(file);
                break;
            }
        }

    }




}

package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    private FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Created FileService Bean");
    }

    public void addFile(File file, int userId) throws FilewithNameExists {

        if(filesMapper.getFileName(file.getFilename(), userId) == null) {
            filesMapper.addFile(file);
        }
        else {
            throw new FilewithNameExists();
        }

        System.out.println("File was added to Database: " + file.getFilename());
    }

    public List<File> getFiles(int userId) {
        return filesMapper.getAllFiles(userId);

    }

    public void deleteFile(int id) {
        filesMapper.delete(id);

    }

    public File getFile(int id) {
        return filesMapper.getFile(id);
    }

    public boolean fileAvailable(String fileName, int userId) {
        if(filesMapper.getFileName(fileName, userId) != null) {
            return true;
        }
        else {
            return false;
        }
    }



}

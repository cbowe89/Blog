package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.util.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/upload")
public class FileUploaderController {
    @PostMapping("/image")
    public String saveImage(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = copyFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }

    private String copyFile(MultipartFile file) throws Exception {
        String url = null;
        String fileName = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            Path path = FileUtil.getImagePath(fileName);
            Files.copy(inputStream, path);
            url = FileUtil.getImageUrl(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Failed to upload image.");
        }
        return url;
    }
}

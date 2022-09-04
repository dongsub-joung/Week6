package com.example.week6.controller;


import com.example.week6.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController

public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/api/upload")
    public String uploadImage(@RequestPart MultipartFile file) {
        return fileUploadService.uploadImage(file);
    }
}

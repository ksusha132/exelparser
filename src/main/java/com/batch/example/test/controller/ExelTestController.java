package com.batch.example.test.controller;

import com.batch.example.test.service.UploadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExelTestController {

    private final UploadService uploadService;

    public ExelTestController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping(value = "/upload")
    public String uploadData() throws IOException {
        uploadService.uploadFile();
        return "DONE";
    }

    @GetMapping(value = "/getCount")
    public String getCount() throws IOException {
        uploadService.createBatchFiles();
        return "DONE";
    }
}

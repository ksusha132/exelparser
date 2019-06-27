package com.batch.example.test.controller;

import com.batch.example.test.service.UploadExelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExelTestController {

    private final UploadExelService uploadExelService;

    public ExelTestController(UploadExelService uploadExelService) {
        this.uploadExelService = uploadExelService;
    }

    @GetMapping(value = "/upload")
    public String uploadExel() throws IOException {
        uploadExelService.uploadFile();
        return "DONE";
    }
}

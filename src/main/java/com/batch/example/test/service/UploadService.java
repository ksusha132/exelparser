package com.batch.example.test.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UploadService {
    void uploadFile() throws IOException;

    void createBatchFiles();
}

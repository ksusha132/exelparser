package com.batch.example.test.service;

import com.batch.example.test.dto.Index;

import java.io.IOException;
import java.util.List;

public interface ExelCreator {
    void createExel(String name, List<Index> indexes) throws IOException;
}

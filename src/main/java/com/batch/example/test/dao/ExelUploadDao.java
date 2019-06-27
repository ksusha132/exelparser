package com.batch.example.test.dao;

import com.batch.example.test.dto.ExelDataObject;

import java.util.List;

public interface ExelUploadDao {
    void insertBatch(List<ExelDataObject> elements);
}

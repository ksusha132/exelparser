package com.batch.example.test.dao;

import com.batch.example.test.dto.Index;

import java.util.List;

public interface UploadDao {
    void insertBatch(List<Index> elements);

    Integer getRowCount();

    List<Index> getElementsByRange(Integer from, Integer to);
}

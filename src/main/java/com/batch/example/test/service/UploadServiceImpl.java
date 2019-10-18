package com.batch.example.test.service;

import com.batch.example.test.dao.UploadDao;
import com.batch.example.test.dto.Index;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    private static final int BATCH_SIZE = 10000;
    private final UploadDao uploadDao;

    public UploadServiceImpl(UploadDao uploadDao) {
        this.uploadDao = uploadDao;
    }

    @Override
    public void uploadFile() throws IOException {
        uploadDao.insertBatch(createElementList());
    }

    private List<Index> createElementList() {
        List<Index> list = new ArrayList<>();
        for (int i = 1; i <= 1135; i++) {
            Index index = new Index();
            index.setId(i);
            list.add(index);
        }

        return list;
    }

    @Override
    public void createBatchFiles() {
        Integer rowCount = uploadDao.getRowCount();
        double batch = 500;
        double iterationsCount = Math.ceil((double) rowCount / batch);
        int offset = 0;
        int limit = (int) batch;

        for (int i = 0; i < iterationsCount; i++) {
            uploadDao.getElementsByRange(limit, offset);
            if (i == iterationsCount - 1) {
                uploadDao.getElementsByRange(rowCount - (limit * i), offset);
            }
            offset += batch;
        }
    }
}

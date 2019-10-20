package com.batch.example.test.service;

import com.batch.example.test.dao.UploadDao;
import com.batch.example.test.dto.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    private final UploadDao uploadDao;

    private ExelCreator exelCreator;

    public UploadServiceImpl(UploadDao uploadDao, ExelCreator exelCreator) {
        this.uploadDao = uploadDao;
        this.exelCreator = exelCreator;
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
    public void createBatchFiles() throws IOException {
        Integer rowCount = uploadDao.getRowCount();
        double batch = 500;
        double iterationsCount = Math.ceil((double) rowCount / batch);
        int offset = 0;
        int limit = (int) batch;

        for (int i = 0; i < iterationsCount; i++) {

            if (i == iterationsCount - 1) {
                callExelCreator(offset, rowCount - (limit * i), i);
                break;
            }

            callExelCreator(offset, limit, i);
            offset += batch;
        }
    }

    private void callExelCreator(int offset, int limit, int i) throws IOException {
        exelCreator.createExel("file" + i, uploadDao.getElementsByRange(limit, offset));
    }
}

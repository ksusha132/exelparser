package com.batch.example.test.service;

import com.batch.example.test.dao.ExelUploadDao;
import com.batch.example.test.dto.ExelDataObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadExelService {

    private static final int BATCH_SIZE = 10000;
    private final ExelUploadDao exelUploadDao;

    public UploadServiceImpl(ExelUploadDao exelUploadDao) {
        this.exelUploadDao = exelUploadDao;
    }

    @Override
    public void uploadFile() throws IOException {
        String pathToExelFile = "/Users/erastovakseniia/Desktop/exel/test.xls";
        FileInputStream file = new FileInputStream(new File(pathToExelFile));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Integer rowCount = sheet.getPhysicalNumberOfRows();
        Integer batchSize = BATCH_SIZE;
        int batchCount = ((int) Math.ceil(rowCount / batchSize) + 1);
        int indexFrom = 1;
        int indexTo = indexFrom + BATCH_SIZE;

        for (int j = 1; j <= batchCount; j++) {
            if (j == batchCount) {
                exelUploadDao.insertBatch(parseExelFileByPart(sheet, indexFrom, rowCount));
            } else {
                exelUploadDao.insertBatch(parseExelFileByPart(sheet, indexFrom, indexTo));
                indexFrom += BATCH_SIZE;
                indexTo += BATCH_SIZE;
            }
        }
    }

    private List<ExelDataObject> parseExelFileByPart(Sheet sheet, int from, int to) throws IOException {

        List<ExelDataObject> objects = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            Row row = sheet.getRow(i);

            ExelDataObject element = new ExelDataObject();
            element.setId(i);
            element.setMrc(row.getCell(0).toString());
            element.setUfps(row.getCell(1).toString());
            element.setPochtamt(row.getCell(2).toString());
            element.setPostOfficeIndex(row.getCell(3).toString());
            element.setPostOfficeBox(row.getCell(4).toString());
            objects.add(element);
        }

        return objects;
    }
}

package com.batch.example.test.service;

import com.batch.example.test.dto.Index;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExelCreatorImpl implements ExelCreator {

    @Override
    public void createExel(String name, List<Index> indexes) throws IOException {

        Workbook workbook = createAndFillExel(indexes);
        OutputStream outputStream = createFile(name);

        workbook.write(outputStream);
        workbook.close();
    }

    private Workbook createAndFillExel(List<Index> indexes) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Index");

        createExelHeader(sheet, "IdIndex");
        fillInnExel(indexes, sheet);

        return workbook;
    }

    private void createExelHeader(Sheet sheet, String cellValue) {
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue(cellValue);
    }

    private void fillInnExel(List<Index> indexes, Sheet sheet) {
        for (int i = 0; i < indexes.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(indexes.get(i).getId()));
        }
    }

    private OutputStream createFile(String name) throws FileNotFoundException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + name + ".xls";

        return new FileOutputStream(fileLocation);
    }
}

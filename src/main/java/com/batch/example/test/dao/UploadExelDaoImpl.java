package com.batch.example.test.dao;

import com.batch.example.test.dto.ExelDataObject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UploadExelDaoImpl implements ExelUploadDao {

    private final JdbcTemplate jdbcTemplate;

    public UploadExelDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertBatch(List<ExelDataObject> elements) {
        String sql = "INSERT INTO POST_OFFICE_BOX (id, mrc, ufps, pochtamt, postOfficeIndex, postOfficeBox) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ExelDataObject dataObjects = elements.get(i);
                ps.setLong(1, dataObjects.getId());
                ps.setString(2, dataObjects.getMrc());
                ps.setString(3, dataObjects.getUfps());
                ps.setString(4, dataObjects.getPochtamt());
                ps.setString(5, dataObjects.getPostOfficeIndex());
                ps.setString(6, dataObjects.getPostOfficeBox());
            }

            @Override
            public int getBatchSize() {
                return elements.size();
            }
        });
    }
}

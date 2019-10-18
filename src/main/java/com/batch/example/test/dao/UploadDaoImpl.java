package com.batch.example.test.dao;

import com.batch.example.test.dto.Index;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UploadDaoImpl implements UploadDao {

    private final JdbcTemplate jdbcTemplate;

    public UploadDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertBatch(List<Index> elements) {
        String sql = "INSERT INTO index (id) VALUES (?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Index dataObjects = elements.get(i);
                ps.setLong(1, dataObjects.getId());
            }

            @Override
            public int getBatchSize() {
                return elements.size();
            }
        });
    }

    @Override
    public Integer getRowCount() {
        String sql = "SELECT count(*) FROM index";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{}, Integer.class);
    }

    @Override
    public List<Index> getElementsByRange(Integer limit, Integer offset) {
        String sql = "SELECT * FROM index limit " + limit + " offset " + offset;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Index(rs.getInt("id"))
        );
    }
}

package com.folies.jdbc;

import com.folies.entity.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WriterJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Writer getWriterByBookName(String bookName) {
        return jdbcTemplate.queryForObject(String.format("select writer.id writer_id, writer.first_name writer_first_name, " +
                "writer.second_name writer_second_name, writer.birth_date writer_birth_date from public.writer " +
                "inner join public.book book on writer.id = book.author where book.name = %s",bookName), (rs, rowNum) ->
                new Writer(
                        rs.getInt("writer_id"),
                        rs.getString("writer_first_name"),
                        rs.getString("writer_second_name"),
                        rs.getDate("writer_birth_date")
                ));
    }
}

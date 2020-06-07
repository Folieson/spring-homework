package com.folies.jdbc;

import com.folies.entity.BookJdbcDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class BookJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from public.book", Integer.class);
    }

    public List<BookJdbcDemo> getBooks() {
        return jdbcTemplate.query("select * from public.book", (rs, rowNum) ->
                new BookJdbcDemo(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("release_date"),
                        rs.getInt("author")));
    }

    public List<BookJdbcDemo> getBooksReleaseDateAfter(Date releaseDate) {
        return jdbcTemplate.query(String.format("select * from public.book where release_date >= '%s'",
                new SimpleDateFormat("yyyy-MM-dd").format(releaseDate)), (rs, rowNum) ->
                new BookJdbcDemo(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("release_date"),
                        rs.getInt("author")));
    }
}

package com.folies.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookJdbcDemo {
    private Integer id;
    private String name;
    private Date releaseDate;
    private Integer author;

    public BookJdbcDemo(Integer id, String name, Date releaseDate, Integer author) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.author = author;
    }
}

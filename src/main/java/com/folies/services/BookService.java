package com.folies.services;

import com.folies.entity.Book;
import com.folies.entity.Writer;

import java.util.Date;
import java.util.List;

public interface BookService {
    long getCount();
    List<Book> findAll();
    Book findById(String id);
    Book create(Book book);
    Date getLastBookReleaseDateByAuthorId(String id);
    boolean existsByName(String name);
    List<Book> findByReleaseDateIsNull();
    List<Book> findByWriterSecondName(String secondName);
    void delete(String id);
    List<Book> findByWriter(Writer writer);
    Book update(Book book);
}

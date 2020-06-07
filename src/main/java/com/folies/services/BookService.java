package com.folies.services;

import com.folies.entity.Book;
import com.folies.entity.Writer;

import java.util.Date;
import java.util.List;

public interface BookService {
    long getCount();
    List<Book> findAll();
    Book findById(Object id);
    Book create(Book book);
    Date getLastBookReleaseDateByAuthorId(Object id);
    boolean existsByName(Object name);
    List<Book> findByReleaseDateIsNull();
    List<Book> findByWriterSecondName(Object secondName);
    void delete(Object id);
    List<Book> findByWriter(Writer writer);
}

package com.folies.services.mock;

import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.services.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MockBookService implements BookService {
    @Override
    public long getCount() {
        return 0;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Book findById(String id) {
        return new Book(Integer.valueOf(id),"Foo", Calendar.getInstance().getTime(),
                new Writer(Integer.valueOf(id),"Jane", "Doe", Calendar.getInstance().getTime()));
    }

    @Override
    public Book create(Book book) {
        return book;
    }

    @Override
    public Date getLastBookReleaseDateByAuthorId(String id) {
        return Calendar.getInstance().getTime();
    }

    @Override
    public boolean existsByName(String name) {
        return true;
    }

    @Override
    public List<Book> findByReleaseDateIsNull() {
        return new ArrayList<>();
    }

    @Override
    public List<Book> findByWriterSecondName(String secondName) {
        return new ArrayList<>();
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Book> findByWriter(Writer writer) {
        return new ArrayList<>();
    }

    @Override
    public Book update(Book book) {
        return book;
    }
}

package com.folies.services;

import com.folies.TestConfig;
import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.exceptions.EntityIllegalArgumentException;
import com.folies.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql","classpath:data.sql"})
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private WriterService writerService;

    @Test
    public void getCount() {
        assertEquals(bookService.getCount(), 6);
    }

    @Test
    public void findAll() {
        assertEquals(bookService.findAll().size(), 6);
    }

    @Test
    public void findById() {
        var book = bookService.findById("1");
        assertNotNull(book);
        var bookName = book.getName();
        assertNotNull(bookName);
        assertEquals(bookName,"The Judgment");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNull() {
        bookService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdEmpty() {
        bookService.findById("");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFound() {
        bookService.findById("99");
    }

    @Test
    public void create() {
        var book = bookService
                .create(new Book(7, "foo", Calendar.getInstance().getTime(),writerService.findById("1")));
        assertNotNull(book);
        var bookName = book.getName();
        assertNotNull(bookName);
        assertEquals(bookName, "foo");
        assertEquals(book,bookService.findById("7"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNull() {
        bookService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createIdNull() {
        bookService.create(new Book(null,"foo",null, writerService.findById("1")));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createWriterNull() {
        bookService.create(new Book(7,"foo",null, null));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createWriterIdNull() {
        bookService.create(new Book(7,"foo",null,
                new Writer(null,null,null, null)));
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWriterNotFound() {
        bookService.create(new Book(7,"foo",null,
                new Writer(99,null,null, null)));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNameNull() {
        bookService.create(new Book(7,null,null, writerService.findById("1")));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNameEmpty() {
        bookService.create(new Book(7,"",null, writerService.findById("1")));
    }

    @Test
    public void getLastBookReleaseDateByAuthorId() {
        Date releaseDate = bookService.getLastBookReleaseDateByAuthorId("1");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1927, Calendar.JANUARY, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(dateFormat.format(releaseDate), dateFormat.format(calendar.getTime()));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void getLastBookReleaseDateByAuthorIdNull() {
        bookService.getLastBookReleaseDateByAuthorId(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void getLastBookReleaseDateByAuthorIdEmpty() {
        bookService.getLastBookReleaseDateByAuthorId("");
    }

    @Test(expected = EntityNotFoundException.class)
    public void getLastBookReleaseDateByAuthorIdNotFound() {
        bookService.getLastBookReleaseDateByAuthorId("99");
    }

    @Test
    public void existsByName() {
        assertTrue(bookService.existsByName("America"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void existsByNameNull() {
        bookService.existsByName(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void existsByNameEmpty() {
        bookService.existsByName("");
    }

    @Test
    public void findByReleaseDateIsNull() {
        assertEquals(bookService.findByReleaseDateIsNull().size(), 1);
    }

    @Test
    public void findByWriterSecondName() {
        var books = bookService.findByWriterSecondName("Harris");
        assertEquals(books.size(),2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByWriterSecondNameNull() {
        bookService.findByWriterSecondName(null);
    }

    @Test
    public void delete() {
        bookService.delete("6");
        assertEquals(bookService.getCount(),5);
    }

    @Test
    public void findByWriter() {
        var writer = writerService.findById("1");
        var books = bookService.findByWriter(writer);
        assertEquals(books.size(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByWriterNull() {
        bookService.findByWriter(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByWriterIdNull() {
        bookService.findByWriter(new Writer(null,null,null,null));
    }
}
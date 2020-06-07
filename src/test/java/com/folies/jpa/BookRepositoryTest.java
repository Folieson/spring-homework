package com.folies.jpa;

import com.folies.TestConfig;
import com.folies.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Test
    public void getLastBookReleaseDateByAuthorId() {
        Date releaseDate = bookRepository.getLastBookReleaseDateByAuthorId(1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1927, Calendar.JANUARY, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(dateFormat.format(releaseDate), dateFormat.format(calendar.getTime()));
    }

    @Test
    public void existsByName() {
        assertTrue(bookRepository.existsByName("The Judgment"));
    }

    @Test
    public void findByReleaseDateIsNull() {
        assertEquals(bookRepository.findByReleaseDateIsNull().size(), 0);
    }

    @Test
    public void findByWriterSecondName() {
        assertEquals(bookRepository.findByWriterSecondName("Chekhov").size(), 1);
    }

    @Test
    public void saveBookTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2006, Calendar.DECEMBER,5);
        Book savedBook = bookRepository.save(new Book(6, "test_book", calendar.getTime(), writerRepository.getOne(3)));
        assertEquals(bookRepository.count(), 6);
        bookRepository.delete(savedBook);
        assertEquals(bookRepository.count(), 5);
    }
}
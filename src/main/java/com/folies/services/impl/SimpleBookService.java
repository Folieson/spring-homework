package com.folies.services.impl;

import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.exceptions.EntityIllegalArgumentException;
import com.folies.exceptions.EntityNotFoundException;
import com.folies.jpa.BookRepository;
import com.folies.jpa.WriterRepository;
import com.folies.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleBookService implements BookService {
    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;

    @Autowired
    public SimpleBookService(BookRepository bookRepository, WriterRepository writerRepository) {
        this.bookRepository = bookRepository;
        this.writerRepository = writerRepository;
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(String id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf(id);
        } catch (NumberFormatException exception) {
            throw  new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к нужному типу," +
                    "текст ошибки: %s", exception));
        }
        Optional<Book> optionalBook = bookRepository.findById(parsedId);
        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException(Book.TYPE_NAME, parsedId);
        }
        return optionalBook.get();
    }

    @Override
    public Book create(Book book) {
        if (book == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if (book.getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        if (book.getName() == null) {
            throw new EntityIllegalArgumentException("Наименование книги не может быть null");
        }
        if (book.getName().isBlank()) {
            throw new EntityIllegalArgumentException("Наименование книги не может быть пустым");
        }
        if (book.getWriter() == null) {
            throw new EntityIllegalArgumentException("Автор не может быть null");
        }
        if (book.getWriter().getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор автора не может быть null");
        }
        if (writerRepository.findById(book.getWriter().getId()).isEmpty()) {
            throw new EntityNotFoundException(Writer.TYPE_NAME, book.getWriter().getId());
        }
        return  bookRepository.save(book);
    }

    @Override
    public Date getLastBookReleaseDateByAuthorId(String id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf(id);
        } catch (NumberFormatException exception) {
            throw  new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к нужному типу," +
                    "текст ошибки: %s", exception));
        }
        var lastBookReleaseDate = bookRepository.getLastBookReleaseDateByAuthorId(parsedId);
        if (lastBookReleaseDate == null) {
            throw new EntityNotFoundException("Не найдены книги автора " + parsedId);
        }
        return bookRepository.getLastBookReleaseDateByAuthorId(parsedId);
    }

    @Override
    public boolean existsByName(String name) {
        if (name == null) {
            throw new EntityIllegalArgumentException("Название книги не может быть null");
        }
        if (name.isBlank()) {
            throw new EntityIllegalArgumentException("Фамилия писателя не может быть пустым");
        }
        return bookRepository.existsByName(name);
    }

    @Override
    public List<Book> findByReleaseDateIsNull() {
        return bookRepository.findByReleaseDateIsNull();
    }

    @Override
    public List<Book> findByWriterSecondName(String secondName) {
        if (secondName == null) {
            throw new EntityIllegalArgumentException("Фамилия писателя не может быть null");
        }
        return bookRepository.findByWriterSecondName(secondName);
    }

    @Override
    public void delete(String id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findByWriter(Writer writer) {
        if (writer == null) {
            throw new EntityIllegalArgumentException("Автор не может быть null");
        }
        if (writer.getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор автора не может быть null");
        }
        return bookRepository.findByWriter(writer);
    }

    @Override
    public Book update(Book book) {
        if (book == null) {
            throw new EntityIllegalArgumentException("Изменяемый объект не может быть null");
        }
        if (book.getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        if (book.getName() == null) {
            throw new EntityIllegalArgumentException("Наименование книги не может быть null");
        }
        if (book.getName().isBlank()) {
            throw new EntityIllegalArgumentException("Наименование книги не может быть пустым");
        }
        if (book.getWriter() == null) {
            throw new EntityIllegalArgumentException("Автор не может быть null");
        }
        if (book.getWriter().getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор автора не может быть null");
        }
        if (bookRepository.findById(book.getId()).isEmpty()) {
            throw new EntityNotFoundException(Book.TYPE_NAME, book.getId());
        }
        return bookRepository.save(book);
    }
}

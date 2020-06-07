package com.folies.services;

import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.exceptions.EntityAlreadyExistsException;
import com.folies.exceptions.EntityHasDetailsException;
import com.folies.exceptions.EntityIllegalArgumentException;
import com.folies.exceptions.EntityNotFoundException;
import com.folies.jpa.BookRepository;
import com.folies.jpa.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleWriterService implements WriterService{
    private final WriterRepository writerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public SimpleWriterService(WriterRepository writerRepository,BookRepository bookRepository) {
        this.writerRepository = writerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    @Override
    public List<Writer> findBySecondName(Object secondName) {
        if (secondName == null) {
            throw new EntityIllegalArgumentException("Фамилия писателя не может быть null");
        }
        String secondNameStr = String.valueOf(secondName);
        if (secondNameStr.isBlank()) {
            throw new EntityIllegalArgumentException("Фамилия писателя не может быть пустой");
        }
        return writerRepository.findBySecondName(secondNameStr);
    }

    @Override
    public Writer findById(Object id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf((String) id);
        } catch (NumberFormatException exception) {
            throw  new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к нужному типу," +
                    "текст ошибки: %s", exception));
        }
        Optional<Writer> optionalWriter;
        optionalWriter = writerRepository.findById(parsedId);
        if (optionalWriter.isEmpty()) {
            throw new EntityNotFoundException(Writer.TYPE_NAME, parsedId);
        }
        return optionalWriter.get();
    }

    @Override
    public Writer create(Writer writer) {
        if (writer == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if (writer.getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Optional<Writer> existedWriter = writerRepository.findById(writer.getId());
        if (existedWriter.isPresent()) {
            throw new EntityAlreadyExistsException(Writer.TYPE_NAME, writer.getId());
        }
        return  writerRepository.save(writer);
    }

    @Override
    public void delete(Object id) {
        Writer writer = findById(id);
        List<Book> books = bookRepository.findByWriter(writer);
        if (!books.isEmpty()) {
            throw new EntityHasDetailsException(Book.TYPE_NAME, writer.getId());
        }
        writerRepository.delete(writer);
    }
}

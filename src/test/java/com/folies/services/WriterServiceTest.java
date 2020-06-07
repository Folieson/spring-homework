package com.folies.services;

import com.folies.TestConfig;
import com.folies.entity.Writer;
import com.folies.exceptions.EntityAlreadyExistsException;
import com.folies.exceptions.EntityHasDetailsException;
import com.folies.exceptions.EntityIllegalArgumentException;
import com.folies.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = com.folies.services.WriterService.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql","classpath:data.sql"})
public class WriterServiceTest {
    @Autowired
    private WriterService writerService;

    @Test
    public void findAll() {
        var writers = writerService.findAll();
        assertEquals(writers.size(),4);
    }

    @Test
    public void findBySecondName() {
        var writers = writerService.findBySecondName("Chekhov");
        assertEquals(writers.size(),1);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findBySecondNameNull() {
        writerService.findBySecondName(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findBySecondNameEmpty() {
        writerService.findBySecondName("");
    }

    @Test
    public void findById() {
        var writer = writerService.findById("1");
        assertNotNull(writer);
        var writerSecondName = writer.getSecondName();
        assertNotNull(writerSecondName);
        assertEquals(writerSecondName,"Kafka");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNull() {
        writerService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdEmpty() {
        writerService.findById("");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFound() {
        writerService.findById("99");
    }

    @Test
    public void create() {
        var writer = writerService
                .create(new Writer(5, "foo", "bar", Calendar.getInstance().getTime()));
        assertNotNull(writer);
        var writerFirstName = writer.getFirstName();
        assertNotNull(writerFirstName);
        assertEquals(writerFirstName, "foo");
        assertEquals(writer,writerService.findById("5"));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNull() {
        writerService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createIdNull() {
        writerService.create(new Writer(null,null,null, null));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExists() {
        var writer = writerService.findById("1");
        writerService.create(writer);
    }

    @Test
    public void delete() {
        writerService.delete("4");
    }

    @Test(expected = EntityHasDetailsException.class)
    public void deleteHasDetails() {
        writerService.delete("1");
        assertEquals(writerService.findAll().size(), 3);
    }
}
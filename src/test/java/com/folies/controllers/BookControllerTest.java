package com.folies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.services.mock.MockBookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookController.class, MockBookService.class})
public class BookControllerTest {
    @Autowired
    private BookController bookController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/book";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(bookController).build();
    }

    @Test
    public void count() throws Exception {
        mockMvc.perform(get(URL + "/count")).andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get(URL + "/1")).andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        var book = new Book(1,"Foo", Calendar.getInstance().getTime(),
                new Writer(1,"Jane", "Doe", Calendar.getInstance().getTime()));
        var requestJson = mapper.writeValueAsString(book);
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void getLastBookReleaseDateByAuthorId() throws Exception {
        mockMvc.perform(get(URL + "/date/last/by/author/1")).andExpect(status().isOk());
    }

    @Test
    public void existByName() throws Exception {
        mockMvc.perform(get(URL + "/exists/by/name/Bar")).andExpect(status().isOk());
    }

    @Test
    public void findByReleaseDateIsNull() throws Exception {
        mockMvc.perform(get(URL + "/find/by/notreleased")).andExpect(status().isOk());
    }

    @Test
    public void findByWriterSecondName() throws Exception {
        mockMvc.perform(get(URL + "/find/by/author/secondName/Bar")).andExpect(status().isOk());
    }

    @Test
    public void findByWriter() throws Exception {
        var writer = new Writer(1,"Jane", "Doe", Calendar.getInstance().getTime());
        var requestJson = mapper.writeValueAsString(writer);
        mockMvc.perform(
                get(URL + "/find/by/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        var book = new Book(1,"Foo", Calendar.getInstance().getTime(),
                new Writer(1,"Jane", "Doe", Calendar.getInstance().getTime()));
        var requestJson = mapper.writeValueAsString(book);
        mockMvc.perform(
                put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/1")).andExpect(status().isNoContent());
    }
}
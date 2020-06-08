package com.folies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folies.entity.Writer;
import com.folies.services.mock.MockWriterService;
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
@SpringBootTest(classes = {WriterController.class, MockWriterService.class})
public class WriterControllerTest {
    @Autowired
    private WriterController writerController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/writer";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(writerController).build();
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
    }

    @Test
    public void findBySecondName() throws Exception {
        mockMvc.perform(get(URL + "/find/by/secondName/Bar")).andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get(URL + "/1")).andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        var writer = new Writer(3,"Jane", "Doe", Calendar.getInstance().getTime());
        var requestJson = mapper.writeValueAsString(writer);
        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        var writer = new Writer(3,"Jane", "Doe", Calendar.getInstance().getTime());
        var requestJson = mapper.writeValueAsString(writer);
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
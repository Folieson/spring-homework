package com.folies.services.mock;

import com.folies.entity.Writer;
import com.folies.services.WriterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class MockWriterService implements WriterService {
    @Override
    public List<Writer> findAll() {
        return new ArrayList<>();
    }

    @Override
    public List<Writer> findBySecondName(String secondName) {
        return new ArrayList<>();
    }

    @Override
    public Writer findById(String id) {
        return new Writer(Integer.valueOf(id),"Jane", "Doe", Calendar.getInstance().getTime());
    }

    @Override
    public Writer create(Writer writer) {
        return writer;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Writer update(Writer writer) {
        return writer;
    }
}

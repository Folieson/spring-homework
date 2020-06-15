package com.folies.services;

import com.folies.entity.Writer;

import java.util.List;

public interface WriterService {
    List<Writer> findAll();
    List<Writer> findBySecondName(String secondName);
    Writer findById(String id);
    Writer create(Writer writer);
    void delete(String id);
    Writer update(Writer writer);
}

package com.folies.services;

import com.folies.entity.Writer;

import java.util.List;

public interface WriterService {
    List<Writer> findAll();
    List<Writer> findBySecondName(Object secondName);
    Writer findById(Object id);
    Writer create(Writer writer);
    void delete(Object id);
}

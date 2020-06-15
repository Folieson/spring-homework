package com.folies.controllers;

import com.folies.entity.Writer;
import com.folies.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/writer")
public class WriterController {
    private final WriterService writerService;

    @Autowired
    public WriterController(@Qualifier("simpleWriterService") WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping
    @PreAuthorize("hasPermission('writer', 'read')")
    public List<Writer> findAll() {
        return writerService.findAll();
    }

    @GetMapping("/find/by/secondName/{secondName}")
    @PreAuthorize("hasPermission('writer', 'read')")
    public List<Writer> findBySecondName(@PathVariable String secondName) {
        return writerService.findBySecondName(secondName);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('writer', 'read')")
    public Writer findById(@PathVariable String id) {
        return writerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermission('writer', 'create')")
    public Writer create(@RequestBody Writer writer) {
        return writerService.create(writer);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('writer', 'update')")
    public Writer update(@RequestBody Writer writer) {
        return writerService.update(writer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission('writer', 'delete')")
    public void delete(@PathVariable String id) {
        writerService.delete(id);
    }
}

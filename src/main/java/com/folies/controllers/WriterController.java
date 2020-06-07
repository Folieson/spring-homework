package com.folies.controllers;

import com.folies.entity.Writer;
import com.folies.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/writer")
public class WriterController {
    private final WriterService writerService;

    @Autowired
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }


    @GetMapping("/all")
    public List<Writer> getWriters() {
        return writerService.findAll();
    }

//    @GetMapping("/findByTheJudgmentName")
//    public Writer getWriterByBookName() {
//        return writerService.findByBookName("The Judgment");
//    }
}

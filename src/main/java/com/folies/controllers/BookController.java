package com.folies.controllers;

import com.folies.entity.BookJdbcDemo;
import com.folies.entity.Book;
import com.folies.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/count")
	public Long getBooksCount() {
		return bookService.getCount();
	}

//	@GetMapping("/all")
//	public List<BookJdbcDemo> getBooks() {
//		return bookService.getAll();
//	}
//
//	@GetMapping("/released_after_1999")
//	public List<BookJdbcDemo> getBooksReleasedAfter1999() {
//		return bookService.getBooksReleasedAfter1999();
//	}

	@GetMapping("/jpa")
	public List<Book> getBooksJpa() {
		return bookService.findAll();
	}

	@PostMapping("/jpa")
	public Book addBookJpa(@RequestBody Book book) {
		return bookService.create(book);
	}

	@GetMapping("/jpa/last/releaseDate/kafka")
	public Date getKafkaLastBookReleaseDate() {
		return bookService.getLastBookReleaseDateByAuthorId(1);
	}

	@GetMapping("/jpa/exists/thejudgment")
	public boolean existByNameTheJudgment() {
		return bookService.existsByName("The Judgment");
	}

	@GetMapping("/jpa/notreleased")
	public List<Book> findByReleaseDateIsNull() {
		return bookService.findByReleaseDateIsNull();
	}

	@GetMapping("jpa/writtenByKafka")
	public List<Book> findWrittenByKafka() {
		return bookService.findByWriterSecondName("Kafka");
	}
}

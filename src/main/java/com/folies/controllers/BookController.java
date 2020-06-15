package com.folies.controllers;

import com.folies.entity.Book;
import com.folies.entity.Writer;
import com.folies.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
	private final BookService bookService;

	@Autowired
	public BookController(@Qualifier("simpleBookService") BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/count")
	@PreAuthorize("hasPermission('book', 'read')")
	public Long count() {
		return bookService.getCount();
	}

	@GetMapping
	@PreAuthorize("hasPermission('book', 'read')")
	public List<Book> findAll() {
		return bookService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasPermission('book', 'read')")
	public Book findById(@PathVariable String id) {
		return bookService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasPermission('book', 'create')")
	public Book create(@RequestBody Book book) {
		return bookService.create(book);
	}

	@GetMapping("/date/last/by/author/{id}")
	@PreAuthorize("hasPermission('book', 'read')")
	public Date getLastBookReleaseDateByAuthorId(@PathVariable String id) {
		return bookService.getLastBookReleaseDateByAuthorId(id);
	}

	@GetMapping("/exists/by/name/{name}")
	@PreAuthorize("hasPermission('book', 'read')")
	public boolean existByName(@PathVariable String name) {
		return bookService.existsByName(name);
	}

	@GetMapping("/find/by/notreleased")
	@PreAuthorize("hasPermission('book', 'read')")
	public List<Book> findByReleaseDateIsNull() {
		return bookService.findByReleaseDateIsNull();
	}

	@GetMapping("/find/by/author/secondName/{secondName}")
	@PreAuthorize("hasPermission('book', 'read')")
	public List<Book> findByWriterSecondName(@PathVariable String secondName) {
		return bookService.findByWriterSecondName(secondName);
	}

	@GetMapping("/find/by/author")
	@PreAuthorize("hasPermission('book', 'read')")
	public List<Book> findByWriter(@RequestBody Writer writer) {
		return bookService.findByWriter(writer);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasPermission('book', 'update')")
	public Book update(@RequestBody Book product) {
		return bookService.update(product);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasPermission('book', 'delete')")
	public void delete(@PathVariable String id) {
		bookService.delete(id);
	}
}

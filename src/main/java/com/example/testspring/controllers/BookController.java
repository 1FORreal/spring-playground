package com.example.testspring.controllers;

import com.example.testspring.domain.Book;
import com.example.testspring.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity
                .ok(this.bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity
                .ok(this.bookService.findBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(
            @RequestBody Book book
    ) {
        Book savedBook = this.bookService.createBook(book);

        return ResponseEntity
                .created(this.getLocation(savedBook.getId()))
                .body(savedBook);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(
            @RequestBody Book book
    ) {
        Book updatedBook = this.bookService.updateBook(book);

        return ResponseEntity
                .created(this.getLocation(updatedBook.getId()))
                .body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeBookById(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(this.bookService.removeBookById(id));
    }

    private URI getLocation(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}

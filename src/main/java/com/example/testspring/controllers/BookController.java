package com.example.testspring.controllers;

import com.example.testspring.domain.dtos.BookDto;
import com.example.testspring.domain.entities.Book;
import com.example.testspring.exceptions.ResourceNotFoundException;
import com.example.testspring.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> findAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();

        this.bookService.findAllBooks().stream()
                .forEach(book -> {
                    BookDto bookDto = this.modelMapper.map(book, BookDto.class);
                    bookDtos.add(bookDto);
                });

        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<BookDto> findBookById(
            @PathVariable Long id
=======
    public ResponseEntity<Book> findBookById(
            @PathVariable(name = "id") Long id
>>>>>>> 97c69f49ab0a6cef2c35b6b14fae08bacdc78d6a
    ) {
        BookDto bookDto = this.modelMapper.map(this.bookService.findBookById(id), BookDto.class);

        return ResponseEntity
                .ok(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(
            @Valid @RequestBody BookDto bookDto
    ) {
        Book toSave = this.modelMapper.map(bookDto, Book.class);

        BookDto savedBookDto = this.modelMapper.map(this.bookService.createBook(toSave), BookDto.class);

        return ResponseEntity
                .created(this.getLocation(savedBookDto.getId()))
                .body(savedBookDto);
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(
            @Valid @RequestBody BookDto bookDto
    ) {
        Book toUpdate = this.modelMapper.map(bookDto, Book.class);

        BookDto updatedBookDto = this.modelMapper.map(this.bookService.updateBook(toUpdate), BookDto.class);

        return ResponseEntity
                .created(this.getLocation(toUpdate.getId()))
                .body(updatedBookDto);
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

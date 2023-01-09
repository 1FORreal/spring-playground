package com.example.testspring.services;

import com.example.testspring.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();
    Book findBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Book book);
    Boolean removeBookById(Long id);

}

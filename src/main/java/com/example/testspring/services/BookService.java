package com.example.testspring.services;

import com.example.testspring.domain.entities.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();
    List<Book> findAllBooksPaginated(Integer page, Integer size);
    Book findBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Book book);
    Boolean removeBookById(Long id);

}

package com.example.testspring.services.impl;

import com.example.testspring.domain.Book;
import com.example.testspring.exceptions.IncorrectIdException;
import com.example.testspring.exceptions.OperationFailedException;
import com.example.testspring.exceptions.ResourceNotFoundException;
import com.example.testspring.repositories.BookRepository;
import com.example.testspring.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        if(!this.isIdValid(id)) throw new IncorrectIdException();

        Optional<Book> foundBook = this.bookRepository.findById(id);

        return foundBook
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Book createBook(Book book) {
        if(book.getId() != null) throw new OperationFailedException();

        return this.bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        if(!this.doesIdExist(book.getId())) throw new OperationFailedException();

        return this.bookRepository.save(book);
    }

    @Override
    public Boolean removeBookById(Long id) {
        if(!this.doesIdExist(id)) return false;

        this.bookRepository.deleteById(id);

        if(this.doesIdExist(id)) throw new OperationFailedException();

        return true;
    }

    public Boolean doesIdExist(Long id) {
        if(!isIdValid(id)) throw new IncorrectIdException();

        return this.bookRepository.existsBookById(id);
    }

    public Boolean isIdValid(Long id) {
        if(id == null) return false;
        if(id < 0) return false;

        return true;
    }
}

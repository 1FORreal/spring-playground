package com.example.testspring.aspects;

import com.example.testspring.domain.entities.Book;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class BookServiceLoggerAspect {

    @Around("execution(public * com.example.testspring.services.impl.BookServiceImpl.findAllBooks())")
    public List<Book> aroundFindAllBooks(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        List<Book> books = (List<Book>) proceedingJoinPoint.proceed();

        log.info("All books have been requested. Total size: " + books.size());

        return books;
    }

    @Around("execution(public com.example.testspring.domain.entities.Book com.example.testspring.services.impl.BookServiceImpl.findBookById(Long))")
    public Book aroundFindBookById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = (Long) proceedingJoinPoint.getArgs()[0];
        Book book = (Book) proceedingJoinPoint.proceed();

        log.info("The book with id=" + id + " has been requested. Requested book: " + book.toString());

        return book;
    }

    @Around("execution(public com.example.testspring.domain.entities.Book com.example.testspring.services.impl.BookServiceImpl.createBook(com.example.testspring.domain.entities.Book))")
    public Book createBook(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Book savedBook = (Book) proceedingJoinPoint.proceed();

        log.info("A new book has been saved " + savedBook.toString());

        return savedBook;
    }

    @Around("execution(public com.example.testspring.domain.entities.Book com.example.testspring.services.impl.BookServiceImpl.updateBook(com.example.testspring.domain.entities.Book))")
    public Book aroundUpdateBook(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = ((Book) proceedingJoinPoint.getArgs()[0]).getId();
        Book updatedBook = (Book) proceedingJoinPoint.proceed();

        log.info("The book with id=" + id + " has been updated. The new modified book: " + updatedBook.toString());

        return updatedBook;
    }

    @Around("execution(public Boolean com.example.testspring.services.impl.BookServiceImpl.removeBookById(Long))")
    public Boolean aroundRemoveBookById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long id = (Long) proceedingJoinPoint.getArgs()[0];
        Boolean removeResult = (Boolean) proceedingJoinPoint.proceed();

        if(removeResult) log.info("The book with id=" + id + " has been deleted");
        else log.info("The book with id=" + id + " has not been deleted");

        return removeResult;
    }

}

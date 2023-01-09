package com.example.testspring.repositories;

import com.example.testspring.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Boolean existsBookById(Long id);

}

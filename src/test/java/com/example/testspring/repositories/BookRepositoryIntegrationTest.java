package com.example.testspring.repositories;

import com.example.testspring.domain.entities.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("BookRepository integration tests")
class BookRepositoryIntegrationTest {
    @Autowired private BookRepository bookRepository;
    private static Long lastAddedBookIndex = 0L;

    @BeforeEach
    void init() {
        List<Book> books = List.of(
                new Book(
                        1L,
                        "Witcher: Last Wish",
                        "First short-story book"
                ),
                new Book(
                        2L,
                        "Witcher: Sword of Destiny",
                        "Second short-story book"
                ),
                new Book(
                        3L,
                        "Witcher: Blood of elves",
                        "First novel"
                )
        );

        bookRepository.saveAll(books);
        lastAddedBookIndex+=3;
    }

    @AfterEach
    void clear() {
        bookRepository.deleteAll();
    }

    @Test
    void should_not_exist_book_with_specific_id() {
        Long id = lastAddedBookIndex + 1;

        assertFalse(bookRepository.existsBookById(id));
    }

    @Test
    void should_exist_book_with_specific_id() {
        Long id = lastAddedBookIndex;

        assertTrue(bookRepository.existsBookById(id));
    }
}
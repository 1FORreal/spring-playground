package com.example.testspring.services.impl;

import com.example.testspring.domain.entities.Book;
import com.example.testspring.exceptions.IncorrectIdException;
import com.example.testspring.exceptions.OperationFailedException;
import com.example.testspring.exceptions.ResourceNotFoundException;
import com.example.testspring.repositories.BookRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookServiceImpl unit tests")
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Nested
    @DisplayName("\"findAllBooks()\" method")
    class FindAllBooks {
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

        @Test
        void should_return_list_of_three_books() {
            Integer expectedNumberOfBooks = 3;

            when(bookRepository.findAll()).thenReturn(books);

            Integer actualNumberOfBooks = bookServiceImpl.findAllBooks().size();

            verify(bookRepository).findAll();
            assertEquals(expectedNumberOfBooks, actualNumberOfBooks);
        }

        @Test
        void should_return_empty_list_of_books() {
            Integer expectedNumberOfBooks = 0;

            when(bookRepository.findAll()).thenReturn(List.of());

            Integer actualNumberOfBooks = bookServiceImpl.findAllBooks().size();

            verify(bookRepository).findAll();
            assertEquals(expectedNumberOfBooks, actualNumberOfBooks);
        }
    }

    @Nested
    @DisplayName("\"findBookById(id)\" method")
    class FindBookById {
        Book book = new Book(
                1L,
                "Witcher: Last Wish",
                "First short-story book!"
        );

        @Test
        void should_return_book_by_id() {
            Long id = 1L;

            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

            Long foundBookWithId = bookServiceImpl.findBookById(id).getId();

            verify(bookRepository).findById(id);
            assertEquals(id, foundBookWithId);
        }

        @ParameterizedTest
        @ValueSource(longs = {-1L})
        @NullSource
        void should_throw_incorrect_id_exception(Long id) {
            assertThrows(IncorrectIdException.class, () -> bookServiceImpl.findBookById(id));
            verify(bookRepository, never()).findById(id);
        }

        @Test
        void should_throw_resource_not_found_exception() {
            when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> bookServiceImpl.findBookById(anyLong()));
            verify(bookRepository).findById(anyLong());
        }
    }

    @Nested
    @DisplayName("\"createBook()\" method")
    class CreateBook {
        Book toSave = new Book(
                null,
                "Witcher: Last Wish",
                "First short-story book!"
        );
        Book savedBook = new Book(
                1L,
                "Witcher: Last Wish",
                "First short-story book!"
        );

        @Test
        void should_create_book() {
            when(bookRepository.save(toSave)).thenReturn(savedBook);

            Book actualSavedBook = bookServiceImpl.createBook(toSave);

            assertEquals(savedBook, actualSavedBook);
            verify(bookRepository).save(toSave);
        }

        @Test
        void should_throw_operation_failed_exception() {
            Book book = new Book(
                    1L,
                    "Witcher: Last Wish",
                    "First shorts-story book!"
            );
            assertThrows(OperationFailedException.class, () -> bookServiceImpl.createBook(book));
        }
    }

    @Nested
    @DisplayName("\"updateBook()\" method")
    class UpdateBook {

        @Test
        @Disabled
        void should_update_book() {
            Book book = new Book(
                    1L,
                    "Witcher: Last Wish",
                    "This is a modified description"
            );

            when(bookRepository.save(book)).thenReturn(book);
            when(bookRepository.existsBookById(book.getId())).thenReturn(true);

            bookServiceImpl.updateBook(book);

            verify(bookRepository).save(book);
        }

        @Test
        void should_throw_operation_failed_exception() {
            Book book = new Book(
                    1L,
                    "Witcher: Last Wish",
                    "This is a modified description"
            );
            Long id = book.getId();

            when(bookServiceImpl.doesIdExist(id)).thenReturn(false);

            assertThrows(OperationFailedException.class, () -> bookServiceImpl.updateBook(book));
            verify(bookRepository, never()).save(book);
        }
    }

    @Nested
    @DisplayName("\"removeBookById()\" method")
    class RemoveBookById {
        @Test
        void should_remove_book_by_id_expected_true() {
            Long id = 1L;

            when(bookRepository.existsBookById(id)).thenReturn(true, false);

            assertTrue(bookServiceImpl.removeBookById(id));
            verify(bookRepository, times(2)).existsBookById(id);
        }

        @Test
        void should_remove_book_by_id_expected_false() {
            Long id = 1L;

            when(bookRepository.existsBookById(id)).thenReturn(false);

            assertFalse(bookServiceImpl.removeBookById(id));
            verify(bookRepository, never()).deleteById(id);
        }

        @Test
        void should_throw_operation_failed_exception() {
            Long id = 1L;

            when(bookRepository.existsBookById(id)).thenReturn(true);

            assertThrows(OperationFailedException.class, () -> bookServiceImpl.removeBookById(id));
            verify(bookRepository, times(2)).existsBookById(id);
        }
    }

    @Nested
    @DisplayName("\"isIdValid(id)\" method")
    class IsIdValid {

        @Test
        void should_return_true() {
            Long id = 1L;

            assertTrue(bookServiceImpl.isIdValid(id));
        }

        @ParameterizedTest
        @ValueSource(longs = {-1L})
        @NullSource
        void should_return_false(Long id) {
            assertFalse(bookServiceImpl.isIdValid(id));
        }
    }

    @Nested
    @DisplayName("\"doesIdExist(id)\" method")
    class DoesIdExist {

        @Test
        void should_return_true() {
            Long id = 1L;

            when(bookRepository.existsBookById(1L)).thenReturn(true);

            assertTrue(bookServiceImpl.doesIdExist(id));
            verify(bookRepository).existsBookById(id);
        }

        @Test
        void should_return_false() {
            Long id = 1L;

            when(bookRepository.existsBookById(1L)).thenReturn(false);

            assertFalse(bookServiceImpl.doesIdExist(id));
            verify(bookRepository).existsBookById(id);
        }

        @Test
        void should_throw_incorrect_id_exception() {
            Long id = -1L;

            assertThrows(IncorrectIdException.class, () -> bookServiceImpl.doesIdExist(id));
            verify(bookRepository, never()).existsBookById(id);

        }
    }
}
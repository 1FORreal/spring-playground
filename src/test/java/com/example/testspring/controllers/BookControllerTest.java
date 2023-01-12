package com.example.testspring.controllers;

import com.example.testspring.domain.dtos.BookDto;
import com.example.testspring.domain.entities.Book;
import com.example.testspring.services.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookController unit tests")
@Disabled
class BookControllerTest {
    @Mock private BookService bookService;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private BookController bookController;

    @Nested
    @DisplayName("\"findAllBooks()\" method")
    class FindAllBooks {
        private List<Book> books = List.of(
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
        void should_return_list_containing_three_books() {
            when(bookService.findAllBooks()).thenReturn(books);

            ResponseEntity<List<BookDto>> returnedBooksResponse = bookController.findAllBooks();

            assertTrue(returnedBooksResponse.getBody().size() == 3);
            verify(bookService).findAllBooks();
        }

        @Test
        void should_return_list_of_empty_books() {
            when(bookService.findAllBooks()).thenReturn(List.of());

            ResponseEntity<List<BookDto>> returnedBooksResponse = bookController.findAllBooks();

            assertTrue(returnedBooksResponse.getBody().size() == 0);
            verify(bookService).findAllBooks();
        }
    }

    @Nested
    @DisplayName("\"findBookById()\" method")
    class GetBookById {
        Long id = 1L;
        Book foundBook = new Book(id, "Witcher: Last Wish", "First short-story");

        @Test
        void should_return_book_by_id() {
            when(bookService.findBookById(id)).thenReturn(foundBook);

            ResponseEntity<BookDto> foundBookResponse = bookController.findBookById(id);

            assertTrue(foundBookResponse.getBody().getId().equals(id));
            verify(bookService).findBookById(id);
        }
    }

    @Nested
    @DisplayName("\"createBook()\" method")
    class CreateBook {
        BookDto toSaveDto = new BookDto(null, "Witcher: Last Wish", "First short-story");
        Book toSave = new Book(null, "Witcher: Last Wish", "first short-story");
        Book savedBook = new Book(1L, "Witcher: Last Wish", "First short-story");
        BookDto savedBookDto = new BookDto(1L, "Witcher: Last Wish", "First short-story");

        @Test
        @Disabled
        void should_return_created_book() {
            when(bookService.createBook(toSave)).thenReturn(savedBook);

            ResponseEntity<BookDto> savedBookResponse = bookController.createBook(toSaveDto);

            assertEquals(toSaveDto, savedBookResponse.getBody());
            verify(bookService).createBook(toSave);
        }
    }

    @Nested
    @DisplayName("\"updateBook\" method")
    class UpdateBook {
        Book toUpdate = new Book(1L, "Witcher: Last Wish", "First short-story");
        BookDto toUpdateDto = new BookDto(1L, "Witcher: Last Wish", "First short-story");

        @Test
        @Disabled
        void should_return_updated_book() {
            when(bookService.updateBook(toUpdate)).thenReturn(toUpdate);

            ResponseEntity<BookDto> updatedBookResponse = bookController.updateBook(toUpdateDto);

            assertEquals(toUpdateDto, updatedBookResponse.getBody());
            verify(bookService).updateBook(toUpdate);
        }
    }

    @Nested
    @DisplayName("\"removeBookById\" method")
    class RemoveBookById {
        Long id = 1L;

        @Test
        void should_remove_book_by_id_expected_true() {
            when(bookService.removeBookById(id)).thenReturn(true);

            assertTrue(bookController.removeBookById(id).getBody());
            verify(bookService).removeBookById(id);
        }

        @Test
        void should_remove_book_by_id_expected_false() {
            when(bookService.removeBookById(id)).thenReturn(false);

            assertFalse(bookController.removeBookById(id).getBody());
            verify(bookService).removeBookById(id);
        }
    }
}
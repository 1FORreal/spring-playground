package com.example.testspring.controllers;

import static org.mockito.Mockito.verify;

import com.example.testspring.domain.entities.Book;
import com.example.testspring.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookController integration tests")
@WebMvcTest // instantiate the web layer instead of the whole context
@Disabled
class BookControllerIntegrationTest {
    @MockBean private BookService bookService;

    @Autowired private MockMvc mockMvc;

    @InjectMocks private BookController bookController;

    @Autowired private ObjectMapper objectMapper;

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
        void should_return_list_of_three_books_from_request() throws Exception {
            RequestBuilder requestBuilder = get("/books")
                    .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON);

            when(bookService.findAllBooks()).thenReturn(books);

            ResultActions resultActions = mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(books)));

            verify(bookService).findAllBooks();
        }
    }

    @Nested
    @DisplayName("\"findBookById()\" method")
    class GetBookById {
        Long id = 1L;
        Book book = new Book(1L, "Witcher: Last Wish", "First short-story");

        @Test
        void should_return_book_by_id_from_request() throws Exception {
            RequestBuilder requestBuilder = get("/books/" + id)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);

            when(bookService.findBookById(id)).thenReturn(book);

            ResultActions resultActions = mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(book)));

            verify(bookService).findBookById(id);
        }
    }

    @Nested
    @DisplayName("\"createBook()\" method")
    class CreateBook {
        Book toSave = new Book(null, "Witcher: Last Wish", "First short-story book");
        Book savedBook = new Book(1L, "Witcher: Last Wish", "First short-story book");

        @Test
        void should_create_book_from_request() throws Exception {
            when(bookService.createBook(toSave)).thenReturn(savedBook);

            RequestBuilder request = post("/books")
                    .content(objectMapper.writeValueAsString(toSave))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            ResultActions actions = mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(savedBook)));

            verify(bookService).createBook(toSave);
        }
    }

    @Nested
    @DisplayName("\"updateBook()\" method")
    class UpdateBook {
        Book toUpdate = new Book(1L, "", "First short-story");
        Book updatedBook = new Book(1L, "Witcher: Last Wish", "First short-story");

        @Test
        void should_update_book_from_request() throws Exception {
            when(bookService.updateBook(updatedBook)).thenReturn(updatedBook);

            RequestBuilder request = put("/books")
                    .content(objectMapper.writeValueAsString(updatedBook))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            ResultActions resultActions = mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(updatedBook)));

            verify(bookService).updateBook(updatedBook);
        }
    }

    @Nested
    @DisplayName("\"removeBookById()\" method")
    class RemoveBook {
        Long id = 1L;

        @Test
        void should_remove_book_by_id_from_request() throws Exception {
            when(bookService.removeBookById(id)).thenReturn(true);

            RequestBuilder requestBuilder = delete("/books/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            ResultActions actions = mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(true)));

            verify(bookService).removeBookById(id);
        }
    }
}
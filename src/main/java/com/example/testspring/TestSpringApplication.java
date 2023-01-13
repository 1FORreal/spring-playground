package com.example.testspring;

import com.example.testspring.domain.entities.Book;
import com.example.testspring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestSpringApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(TestSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Book> bookList = List.of(
				new Book(null, "Witcher: The Last Wish", "First short-story book"),
				new Book(null, "Witcher: Sword of Destiny", "Second short-story book"),
				new Book(null, "Witcher: Blood of Elves", "First novel"),
				new Book(null, "Witcher: Time of contempt", "Second novel"),
				new Book(null, "Witcher: Baptism of fire", "Third novel"),
				new Book(null, "Witcher: The tower of the Swallow", "Forth novel"),
				new Book(null, "Witcher: The Lady of the Lake", "Fifth novel"),
				new Book(null, "Witcher: Season of Storms", "Sixth novel")
		);

		this.bookRepository.saveAll(bookList);
	}
}

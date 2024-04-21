package cc.maids.librarymanagement.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import cc.maids.librarymanagement.models.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = cc.maids.librarymanagement.LibraryManagementApplication.class)
public class BooksControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("password");

        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity("/api/login", authRequest, AuthResponse.class);
        assertThat(authResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        String token = authResponse.getBody().getToken();
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
    }

    @Test
    public void testCreateBook() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("Author Name");

        ResponseEntity<Book> response = restTemplate.postForEntity("/api/books", newBook, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Book createdBook = response.getBody();
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo("New Book");
        assertThat(createdBook.getAuthor()).isEqualTo("Author Name");
    }

    @Test
    public void testGetAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("/api/books", Book[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book[] books = response.getBody();
        assertThat(books).isNotNull();

        List<Book> bookList = Arrays.asList(books);
        bookList.forEach(book -> {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Publication Year: " + book.getPublicationYear());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("------------------------");
        });
    }

    @Test
    public void testGetBookById() {
        ResponseEntity<Book> response = restTemplate.getForEntity("/api/books/1", Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book book = response.getBody();
        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(1);
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");

        restTemplate.put("/api/books/1", updatedBook);

        ResponseEntity<Book> response = restTemplate.getForEntity("/api/books/1", Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book book = response.getBody();
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Updated Title");
        assertThat(book.getAuthor()).isEqualTo("Updated Author");
    }

    @Test
    public void testDeleteBook() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/books/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        ResponseEntity<Book> getResponse = restTemplate.getForEntity("/api/books/1", Book.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

package cc.maids.librarymanagement.tests;

import cc.maids.librarymanagement.models.Book;
import cc.maids.librarymanagement.security.AuthRequest;
import cc.maids.librarymanagement.security.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

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

        HttpEntity<Book> requestEntity = new HttpEntity<>(newBook, headers);
        ResponseEntity<Book> response = restTemplate.postForEntity("/api/books", requestEntity, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Book createdBook = response.getBody();
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo("New Book");
        assertThat(createdBook.getAuthor()).isEqualTo("Author Name");
    }

    @Test
    public void testGetBookById() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/1", HttpMethod.GET, requestEntity, Book.class);
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

        HttpEntity<Book> requestEntity = new HttpEntity<>(updatedBook, headers);
        restTemplate.exchange("/api/books/1", HttpMethod.PUT, requestEntity, Void.class);

        ResponseEntity<Book> response = restTemplate.exchange("/api/books/1", HttpMethod.GET, new HttpEntity<>(headers), Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book book = response.getBody();
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Updated Title");
        assertThat(book.getAuthor()).isEqualTo("Updated Author");
    }

    @Test
    public void testDeleteBook() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/books/1", HttpMethod.DELETE, requestEntity, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Book> getResponse = restTemplate.exchange("/api/books/1", HttpMethod.GET, requestEntity, Book.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

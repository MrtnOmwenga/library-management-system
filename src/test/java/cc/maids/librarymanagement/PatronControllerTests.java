package cc.maids.librarymanagement.tests;

import cc.maids.librarymanagement.models.Patron;
import cc.maids.librarymanagement.security.AuthRequest;
import cc.maids.librarymanagement.security.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = cc.maids.librarymanagement.LibraryManagementApplication.class)
public class PatronControllerTests {

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
    public void testCreatePatron() {
        Patron newPatron = new Patron();
        newPatron.setName("John Doe");
        newPatron.setContactInformation("johndoe@email.com");

        HttpEntity<Patron> requestEntity = new HttpEntity<>(newPatron, headers);
        ResponseEntity<Patron> response = restTemplate.postForEntity("/api/patrons", requestEntity, Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Patron createdPatron = response.getBody();
        assertThat(createdPatron).isNotNull();
        assertThat(createdPatron.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetPatronById() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Patron> response = restTemplate.exchange("/api/patrons/1", HttpMethod.GET, requestEntity, Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Patron patron = response.getBody();
        assertThat(patron).isNotNull();
        assertThat(patron.getId()).isEqualTo(1);
    }

    @Test
    public void testUpdatePatron() {
        Patron updatedPatron = new Patron();
        updatedPatron.setName("Jane Doe");
        updatedPatron.setContactInformation("janedoe@email.com");

        HttpEntity<Patron> requestEntity = new HttpEntity<>(updatedPatron, headers);
        restTemplate.exchange("/api/patrons/1", HttpMethod.PUT, requestEntity, Void.class);

        ResponseEntity<Patron> response = restTemplate.exchange("/api/patrons/1", HttpMethod.GET, requestEntity, Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Patron patron = response.getBody();
        assertThat(patron).isNotNull();
        assertThat(patron.getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testDeletePatron() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/patrons/1", HttpMethod.DELETE, requestEntity, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Patron> getResponse = restTemplate.exchange("/api/patrons/1", HttpMethod.GET, requestEntity, Patron.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

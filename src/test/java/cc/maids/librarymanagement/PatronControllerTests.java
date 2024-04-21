package cc.maids.librarymanagement.tests;

import cc.maids.librarymanagement.models.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = cc.maids.librarymanagement.LibraryManagementApplication.class)
public class PatronControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreatePatron() {
        Patron newPatron = new Patron();
        newPatron.setName("John Doe");

        ResponseEntity<Patron> response = restTemplate.postForEntity("/api/patrons", newPatron, Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Patron createdPatron = response.getBody();
        assertThat(createdPatron).isNotNull();
        assertThat(createdPatron.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetPatronById() {
        ResponseEntity<Patron> response = restTemplate.getForEntity("/api/patrons/1", Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Patron patron = response.getBody();
        assertThat(patron).isNotNull();
        assertThat(patron.getId()).isEqualTo(1);
    }

    @Test
    public void testUpdatePatron() {
        Patron updatedPatron = new Patron();
        updatedPatron.setName("Jane Doe");

        restTemplate.put("/api/patrons/1", updatedPatron);

        ResponseEntity<Patron> response = restTemplate.getForEntity("/api/patrons/1", Patron.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Patron patron = response.getBody();
        assertThat(patron).isNotNull();
        assertThat(patron.getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testDeletePatron() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/patrons/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Patron> getResponse = restTemplate.getForEntity("/api/patrons/1", Patron.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

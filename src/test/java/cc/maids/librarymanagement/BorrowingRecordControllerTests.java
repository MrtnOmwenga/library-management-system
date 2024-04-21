package cc.maids.librarymanagement.tests;

import cc.maids.librarymanagement.models.BorrowingRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = cc.maids.librarymanagement.LibraryManagementApplication.class)
public class BorrowingRecordControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateBorrowingRecord() {
        BorrowingRecord newRecord = new BorrowingRecord();
        newRecord.setBookId(1L);
        newRecord.setPatronId(1L);
        newRecord.setBorrowingDate(java.time.LocalDate.now());
        newRecord.setReturnDate(java.time.LocalDate.now().plusDays(14));

        ResponseEntity<BorrowingRecord> response = restTemplate.postForEntity("/api/borrowing-records", newRecord, BorrowingRecord.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        BorrowingRecord createdRecord = response.getBody();
        assertThat(createdRecord).isNotNull();
        assertThat(createdRecord.getBookId()).isEqualTo(newRecord.getBookId());
        assertThat(createdRecord.getPatronId()).isEqualTo(newRecord.getPatronId());
        assertThat(createdRecord.getBorrowingDate()).isEqualTo(newRecord.getBorrowingDate());
        assertThat(createdRecord.getReturnDate()).isEqualTo(newRecord.getReturnDate());
    }

    @Test
    public void testGetBorrowingRecordById() {
        ResponseEntity<BorrowingRecord> response = restTemplate.getForEntity("/api/borrowing-records/1", BorrowingRecord.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BorrowingRecord record = response.getBody();
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(1);
    }

    @Test
    public void testUpdateBorrowingRecord() {
        BorrowingRecord updatedRecord = new BorrowingRecord();
        updatedRecord.setBookId(2L);
        updatedRecord.setPatronId(2L);
        updatedRecord.setBorrowingDate(java.time.LocalDate.now());
        updatedRecord.setReturnDate(java.time.LocalDate.now().plusDays(7));

        restTemplate.put("/api/borrowing-records/1", updatedRecord);

        ResponseEntity<BorrowingRecord> response = restTemplate.getForEntity("/api/borrowing-records/1", BorrowingRecord.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BorrowingRecord record = response.getBody();
        assertThat(record).isNotNull();
        assertThat(record.getBookId()).isEqualTo(updatedRecord.getBookId());
        assertThat(record.getPatronId()).isEqualTo(updatedRecord.getPatronId());
        assertThat(record.getBorrowingDate()).isEqualTo(updatedRecord.getBorrowingDate());
        assertThat(record.getReturnDate()).isEqualTo(updatedRecord.getReturnDate());
    }

    @Test
    public void testDeleteBorrowingRecord() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/borrowing-records/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<BorrowingRecord> getResponse = restTemplate.getForEntity("/api/borrowing-records/1", BorrowingRecord.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

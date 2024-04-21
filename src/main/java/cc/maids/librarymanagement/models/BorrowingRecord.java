package cc.maids.librarymanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long bookId;

    @NotNull
    private Long patronId;

    @NotNull
    private LocalDate borrowingDate;

    @NotNull
    @FutureOrPresent
    private LocalDate returnDate;

    // Constructors
    public BorrowingRecord() {
        // Default constructor
    }

    public BorrowingRecord(Long id, Long bookId, Long patronId, LocalDate borrowingDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

package cc.maids.librarymanagement.controllers;

import cc.maids.librarymanagement.models.BorrowingRecord;
import cc.maids.librarymanagement.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @GetMapping
    public ResponseEntity<List<BorrowingRecord>> getAllBorrowingRecords() {
        List<BorrowingRecord> records = borrowingRecordService.getAllBorrowingRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecord> getBorrowingRecordById(@PathVariable Long id) {
    return borrowingRecordService.getBorrowingRecordById(id)
        .map(record -> new ResponseEntity<>(record, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BorrowingRecord> createBorrowingRecord(@Valid @RequestBody BorrowingRecord newRecord) {
        BorrowingRecord createdRecord = borrowingRecordService.createBorrowingRecord(newRecord);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowingRecord> updateBorrowingRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecord updatedRecord) {
        BorrowingRecord record = borrowingRecordService.updateBorrowingRecord(id, updatedRecord);
        return record != null
            ? new ResponseEntity<>(record, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@PathVariable Long id) {
        boolean isDeleted = borrowingRecordService.deleteBorrowingRecord(id);
        
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package cc.maids.librarymanagement.services;

import cc.maids.librarymanagement.models.BorrowingRecord;
import cc.maids.librarymanagement.repositories.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public Optional<BorrowingRecord> getBorrowingRecordById(Long id) {
        return borrowingRecordRepository.findById(id);
    }

    @Transactional
    public BorrowingRecord createBorrowingRecord(BorrowingRecord newRecord) {
        return borrowingRecordRepository.save(newRecord);
    }

    @Transactional
    public BorrowingRecord updateBorrowingRecord(Long id, BorrowingRecord updatedRecord) {
        updatedRecord.setId(id);
        return borrowingRecordRepository.save(updatedRecord);
    }

    @Transactional
    public boolean deleteBorrowingRecord(Long id) {
        if (borrowingRecordRepository.existsById(id)) {
            borrowingRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

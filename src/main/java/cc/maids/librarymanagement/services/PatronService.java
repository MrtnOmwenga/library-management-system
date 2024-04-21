package cc.maids.librarymanagement.services;

import cc.maids.librarymanagement.models.Patron;
import cc.maids.librarymanagement.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @Transactional
    public Patron createPatron(Patron newPatron) {
        return patronRepository.save(newPatron);
    }

    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        updatedPatron.setId(id);
        return patronRepository.save(updatedPatron);
    }

    @Transactional
    public boolean deletePatron(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

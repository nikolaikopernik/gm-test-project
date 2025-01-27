package nl.gerimedica.assignment.repositories;

import nl.gerimedica.assignment.repositories.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findBySsn(String ssn);
}

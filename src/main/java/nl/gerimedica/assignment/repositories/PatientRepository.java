package nl.gerimedica.assignment.repositories;

import nl.gerimedica.assignment.repositories.model.PatientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientDto, Long> {
    PatientDto findBySsn(String ssn);
}

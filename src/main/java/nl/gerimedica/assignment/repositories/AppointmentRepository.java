package nl.gerimedica.assignment.repositories;

import nl.gerimedica.assignment.repositories.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "Select a.* from appointment a where a.patient_id=(select id from patient where ssn=?1)", nativeQuery = true)
    List<Appointment> findBySsn(String ssn);

    @Query(value = "Select a from Appointment a where LOWER(a.reason) = LOWER(?1)")
    List<Appointment> findByReason(String reason);
}

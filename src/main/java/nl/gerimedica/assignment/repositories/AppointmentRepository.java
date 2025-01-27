package nl.gerimedica.assignment.repositories;

import nl.gerimedica.assignment.repositories.model.AppointmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDto, Long> {
    @Query(value = "Select a.* from appointmentDto a where a.patient_id=(select id from patientDto where ssn=?1)", nativeQuery = true)
    List<AppointmentDto> findBySsn(String ssn);

    @Query(value = "Select a from AppointmentDto a where LOWER(a.reason) = LOWER(?1)")
    List<AppointmentDto> findByReason(String reason);
}

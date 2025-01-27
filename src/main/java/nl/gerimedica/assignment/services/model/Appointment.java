package nl.gerimedica.assignment.services.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.gerimedica.assignment.repositories.model.PatientDto;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Appointment {
    public Long id;
    public String reason;
    public LocalDate date;
    public String patientName;
    public String patientSsn;
}

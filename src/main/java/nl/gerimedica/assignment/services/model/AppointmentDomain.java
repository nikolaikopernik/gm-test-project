package nl.gerimedica.assignment.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppointmentDomain {
    public Long id;
    public String reason;
    public LocalDate date;
    public String patientName;
    public String patientSsn;
}

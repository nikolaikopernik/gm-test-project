package nl.gerimedica.assignment.services.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class BulkAppointment {
    String reason;
    LocalDate date;
}

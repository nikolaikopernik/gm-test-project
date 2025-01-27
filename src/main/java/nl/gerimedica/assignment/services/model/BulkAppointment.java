package nl.gerimedica.assignment.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BulkAppointment {
    String reason;
    LocalDate date;
}

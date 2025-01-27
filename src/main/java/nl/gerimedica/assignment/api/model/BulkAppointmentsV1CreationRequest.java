package nl.gerimedica.assignment.api.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import nl.gerimedica.assignment.services.model.BulkAppointment;

import java.util.List;

/**
 * Let's not mix POST method, payload and request params
 * and have the full request in payload in a structured object
 */
@Value
@AllArgsConstructor
public class BulkAppointmentsV1CreationRequest {
    String patientName;
    String ssn;
    List<BulkAppointment> appointments;
}

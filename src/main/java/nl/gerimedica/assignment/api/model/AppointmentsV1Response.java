package nl.gerimedica.assignment.api.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import nl.gerimedica.assignment.services.model.AppointmentDomain;

import java.util.List;

/**
 * Object response is more flexible than List as
 * it can be extended without breaking changes
 */
@Value
@AllArgsConstructor
public class AppointmentsV1Response {
    List<AppointmentDomain> appointments;
}

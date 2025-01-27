package nl.gerimedica.assignment;

import nl.gerimedica.assignment.api.model.AppointmentsV1Response;
import nl.gerimedica.assignment.repositories.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BulkCreateAppointmentsTest extends IntegrationTest {
    @Autowired
    private AppointmentRepository repository;

    @Test
    void shouldCreateBulkAppointments() {
        var result = post("/api/v1/appointments/bulk",
                """
                        {
                            "patientName": "Sam",
                            "ssn": "123456",
                            "appointments":[
                                {"reason":"Checkup","date":"2025-01-15"},
                                {"reason":"X-Ray","date":"2025-01-16"}
                            ]
                        }
                        """,
                AppointmentsV1Response.class);

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        var created = repository.findBySsn("123456");
        var ids = created.stream().map(it -> it.id).collect(Collectors.toSet());

        assertThat(created).hasSize(2);
        result.getBody().getAppointments().forEach(it -> {
            assertThat(it.id).isNotNull();
            assertThat(ids).contains(it.id);
        });
    }
}

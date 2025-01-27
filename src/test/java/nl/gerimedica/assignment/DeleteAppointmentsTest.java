package nl.gerimedica.assignment;

import nl.gerimedica.assignment.repositories.AppointmentRepository;
import nl.gerimedica.assignment.services.HospitalService;
import nl.gerimedica.assignment.services.model.BulkAppointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

public class DeleteAppointmentsTest extends IntegrationTest {
    @Autowired
    AppointmentRepository repository;

    @Autowired
    HospitalService service;

    @BeforeEach
    public void init() {
        service.bulkCreateAppointments("Sam", "123456", List.of(
                new BulkAppointment("Checkup", LocalDate.of(2025, 1, 15)),
                new BulkAppointment("X-Ray", LocalDate.of(2025, 1, 15))
        ));

        service.bulkCreateAppointments("Joost", "0134521", List.of(
                new BulkAppointment("CHECKUP", LocalDate.of(2025, 1, 15)),
                new BulkAppointment("Treatment", LocalDate.of(2025, 1, 25))
        ));
    }


    @Test
    void shouldReturnErrorWhenPatientNotFound() {
        HttpClientErrorException thrown = Assertions.assertThrows(HttpClientErrorException.class,
                () -> {
                    restTemplate.exchange("/api/v1/appointments?ssn=UNKNOWN",
                            HttpMethod.DELETE,
                            new HttpEntity<String>(""),
                            String.class);
                }
        );

        org.assertj.core.api.Assertions.assertThat(thrown.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldDeleteAppointmentsWhenPatientFound() {
        var result = restTemplate.exchange("/api/v1/appointments?ssn=123456",
                            HttpMethod.DELETE,
                            new HttpEntity<String>(""),
                            String.class);

        var persisted = repository.findBySsn("123456");
        org.assertj.core.api.Assertions.assertThat(persisted).isEmpty();
    }
}

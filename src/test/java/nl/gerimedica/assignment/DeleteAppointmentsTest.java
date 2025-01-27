package nl.gerimedica.assignment;

import nl.gerimedica.assignment.api.model.AppointmentsV1Response;
import nl.gerimedica.assignment.repositories.AppointmentRepository;
import nl.gerimedica.assignment.services.HospitalService;
import nl.gerimedica.assignment.services.model.BulkAppointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAppointmentsTest extends IntegrationTest {
    @Autowired
    AppointmentRepository repository;

    @Autowired
    HospitalService service;

    @BeforeEach
    public void init(){
        service.bulkCreateAppointments("Sam", "123456", List.of(
                new BulkAppointment("Checkup", LocalDate.of(2025,1, 15)),
                new BulkAppointment("X-Ray", LocalDate.of(2025,1, 15))
        ));

        service.bulkCreateAppointments("Joost", "0134521", List.of(
                new BulkAppointment("CHECKUP", LocalDate.of(2025,1, 15)),
                new BulkAppointment("Treatment", LocalDate.of(2025,1, 25))
        ));
    }


    @Test
    void shouldReturnEmptyAppointments() {
        String url = "/api/v1/appointments?reason=Onderzoek";
        var response = restTemplate.getForEntity(url, AppointmentsV1Response.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getAppointments()).isEmpty();
    }
}

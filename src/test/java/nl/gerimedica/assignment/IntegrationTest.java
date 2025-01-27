package nl.gerimedica.assignment;

import nl.gerimedica.assignment.repositories.AppointmentRepository;
import nl.gerimedica.assignment.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    protected RestTemplate restTemplate;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @LocalServerPort
    int port;

    @BeforeEach
    public void initRestTemplate() {
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .build();

        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
    }

    public <T> ResponseEntity<T> post(String url, Object body, Class<T> responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST,
                requestEntity,
                responseClass);
    }
}

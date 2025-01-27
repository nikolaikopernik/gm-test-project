package nl.gerimedica.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentApplicationTests extends IntegrationTest {

    @Test
    void testSuccess() {
        String url = "/api/appointments-by-reason?keyword=Checkup";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String body = response.getBody();

        assertTrue(body.contains("\"reason\" : \"SomeNonExistentField\""));
    }
}

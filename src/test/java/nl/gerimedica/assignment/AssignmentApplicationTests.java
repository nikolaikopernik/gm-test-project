package nl.gerimedica.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentApplicationTests {
    private RestTemplate restTemplate;

    public AssignmentApplicationTests(@LocalServerPort int port){
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .build();
    }

    @Test
    void testSuccess() {
        String url = "/api/appointments-by-reason?keyword=Checkup";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String body = response.getBody();

        assertTrue(body.contains("\"reason\" : \"SomeNonExistentField\""));
    }
}

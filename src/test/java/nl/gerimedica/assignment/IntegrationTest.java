package nl.gerimedica.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    protected RestTemplate restTemplate;
    @LocalServerPort
    int port;

    @BeforeEach
    public void init() {
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .build();
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

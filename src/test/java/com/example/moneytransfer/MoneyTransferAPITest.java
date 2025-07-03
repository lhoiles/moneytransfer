package com.example.moneytransfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoneyTransferAPITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testTransferEndpoint() throws Exception {
        // Create two accounts first
        Map<String, Object> account1 = new HashMap<>();
        account1.put("id", 1);
        account1.put("name", "Alice");
        account1.put("balance", 200.0);
        restTemplate.postForEntity("http://localhost:" + port + "/api/accounts", account1, String.class);

        Map<String, Object> account2 = new HashMap<>();
        account2.put("id", 2);
        account2.put("name", "Bob");
        account2.put("balance", 50.0);
        restTemplate.postForEntity("http://localhost:" + port + "/api/accounts", account2, String.class);

        // Prepare the transfer request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("fromAccountId", 1);
        requestBody.put("toAccountId", 2);
        requestBody.put("amount", 100.0);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Combine headers and body into a single entity
        HttpEntity<String> request = new HttpEntity<>(
                objectMapper.writeValueAsString(requestBody), headers);

        // Send POST request to the /transfers endpoint
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transfers",
                request,
                String.class
        );

        // Assert response status
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Optional: assert response contains transfer details
        assertThat(response.getBody()).contains("fromAccountId");
        assertThat(response.getBody()).contains("toAccountId");
        assertThat(response.getBody()).contains("amount");
    }

    @Test
    public void testGetTransferById() throws Exception {
        // Create two accounts first
        Map<String, Object> account1 = new HashMap<>();
        account1.put("id", 3);
        account1.put("name", "Charlie");
        account1.put("balance", 300.0);
        restTemplate.postForEntity("http://localhost:" + port + "/api/accounts", account1, String.class);

        Map<String, Object> account2 = new HashMap<>();
        account2.put("id", 4);
        account2.put("name", "Dana");
        account2.put("balance", 100.0);
        restTemplate.postForEntity("http://localhost:" + port + "/api/accounts", account2, String.class);

        // Prepare the transfer request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("fromAccountId", 3);
        requestBody.put("toAccountId", 4);
        requestBody.put("amount", 50.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(
                objectMapper.writeValueAsString(requestBody), headers);

        // Send POST request to the /transfers endpoint
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transfers",
                request,
                Map.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Integer transferId = (Integer) response.getBody().get("id");

        // GET the transfer by id
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/transfers/" + transferId,
                String.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).contains("fromAccountId");
        assertThat(getResponse.getBody()).contains("toAccountId");
        assertThat(getResponse.getBody()).contains("amount");
    }
}

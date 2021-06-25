package io.silverman.springbootwebservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void 메인페이지() {
        // When
        String body = this.restTemplate.getForObject("/", String.class);

        // Then
        assertTrue(body.contains("스프링 부트 웹 서비스"));
    }
}
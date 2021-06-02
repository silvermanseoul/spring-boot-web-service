package io.silverman.springbootwebservice.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTest {

    @Test
    void lombok() {
        // Given
        String name = "test";
        int amount = 1000;

        // When
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // Then
        assertEquals(name, dto.getName());
        assertEquals(amount, dto.getAmount());
    }
}
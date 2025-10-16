package com.chat.gusta;

import com.chat.gusta.service.WhatsAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhatsAppServiceGetStatusTest {

    @Mock
    private RestTemplate restTemplate;

    private WhatsAppService whatsAppService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        whatsAppService = new WhatsAppService(restTemplate);
    }

    @Test
    void deveRetornarStatusDaInstancia() {
        String instanceName = "gusta01";
        String expectedResponse = "CONNECTED";

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(expectedResponse);

        String result = whatsAppService.getStatus(instanceName);

        assertEquals(expectedResponse, result);

        verify(restTemplate, times(1))
                .getForObject(eq("http://localhost:3000/status/" + instanceName), eq(String.class));
    }
}

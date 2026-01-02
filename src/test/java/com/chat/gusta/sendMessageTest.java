package com.chat.gusta;

import com.chat.gusta.service.WhatsAppServiceRotas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class sendMessageTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WhatsAppServiceRotas whatsAppService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveEnviarMensagemComSucesso() {
        // Arrange
        String instanceName = "test123";
        String to = "61991763642";
        String message = "ola!";
        String expectedResponse = "Mensagem enviada com sucesso";

        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(expectedResponse);

        // Act
        String result = whatsAppService.sendMessage(instanceName, to, message);

        // Assert
        assertEquals(expectedResponse, result);

        verify(restTemplate, times(1))
                .postForObject(
                        eq("http://localhost:3000/send/" + instanceName),
                        any(HttpEntity.class),
                        eq(String.class)
                );
    }
}

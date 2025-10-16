package com.chat.gusta.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:3000";

    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Formata o número no padrão do Node/WhatsApp
    private String formatNumber(String num) {
        String n = num.replaceAll("\\D", ""); // remove tudo que não é número
        if (n.length() == 10 || n.length() == 11) n = "55" + n; // adiciona DDI Brasil se faltar
        if (!n.startsWith("55")) n = "55" + n;
        return n + "@c.us";
    }

    public String sendMessage(String instanceName, String to, String message) {
        String url = baseUrl + "/send/" + instanceName;

        // Formata o número antes de enviar
        String formattedNumber = formatNumber(to);

        System.out.println("[JAVA] Enviando mensagem...");
        System.out.println("Número original: " + to);
        System.out.println("Número formatado: " + formattedNumber);
        System.out.println("Mensagem: " + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "number", formattedNumber,
                "message", message
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);



        return restTemplate.postForObject(url, request, String.class);
    }

    public String getStatus(String instance){
        String url = baseUrl + "/status/" + instance;
        return restTemplate.getForObject(url, String.class);
    }

    public String getQrcode(String instance){
        String url = baseUrl + "/qrcode/" + instance;
        return restTemplate.getForObject(url, String.class);
    }

    public String disconnect(String instance){
        String url = baseUrl + "/disconnect/" + instance;
        return restTemplate.postForObject(url, null, String.class);
    }

    public void processIncomingMessage(Map<String, Object> payload){
        String from = (String) payload.get("from");
        String body = (String) payload.get("body");
        System.out.println("Mensagem recebida de " + from + ": " + body);
    }
}

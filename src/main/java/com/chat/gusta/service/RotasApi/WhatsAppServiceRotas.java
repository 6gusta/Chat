package com.chat.gusta.service.RotasApi;


import com.chat.gusta.model.InstanceStatus;
import com.chat.gusta.model.InstanceStatusResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class WhatsAppServiceRotas {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:3000";

    public WhatsAppServiceRotas(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Formata o número no padrão do Node/WhatsApp
    private String formatNumber(String num) {
        String n = num.replaceAll("\\D", ""); // remove tudo que não é número
        if (n.length() == 10 || n.length() == 11) n = "55" + n; // adiciona DDI Brasil se faltar
        if (!n.startsWith("55")) n = "55" + n;
        return n + "@c.us";
    }

    public String sendMessage(String instanceName, String to, String message, MultipartFile image) {
        try {
            String url = baseUrl + "/send/" + instanceName;
            String formattedNumber = formatNumber(to);

            HttpHeaders headers = new HttpHeaders();

            if (image != null && !image.isEmpty()) {
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("toNumber", formattedNumber);
                if (message != null) body.add("message", message);

                body.add("image", new ByteArrayResource(image.getBytes()) {
                    @Override
                    public String getFilename() {
                        return image.getOriginalFilename();
                    }
                });

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                return restTemplate.postForObject(url, requestEntity, String.class);
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
                Map<String, String> body = Map.of(
                        "toNumber", formattedNumber,
                        "message", message != null ? message : ""
                );

                HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
                return restTemplate.postForObject(url, request, String.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao processar arquivo de imagem: " + e.getMessage();
        }
    }




    public String getQrcode(String instance){
        String url = baseUrl + "/qrcode/" + instance;
        return restTemplate.getForObject(url, String.class);
    }

    public String disconnect(String instance){
        String url = baseUrl + "/disconnect/" + instance;
        return restTemplate.postForObject(url, null, String.class);
    }

    public InstanceStatusResponse getStatus(String instanceName) {
        String url = baseUrl + "/status/" + instanceName;
        return restTemplate.getForObject(url, InstanceStatusResponse.class);
    }


    public InstanceStatus mapNodeStatus(InstanceStatusResponse response){
        if (response == null) return InstanceStatus.OFFLINE;
        return response.isWhatsappReady() ? InstanceStatus.CONNECTED: InstanceStatus.QR;
    }




}

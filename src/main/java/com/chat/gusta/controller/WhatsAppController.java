package com.chat.gusta.controller;


import com.chat.gusta.model.WhatsAppMessage;
import com.chat.gusta.repository.MensagensRotas.MessageRepository;

import com.chat.gusta.service.RotasApi.WhatsAppServiceRotas;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"}, allowCredentials = "true")
public class WhatsAppController {

    private final WhatsAppServiceRotas whatsAppService;
    private final MessageRepository messageRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    public WhatsAppController(WhatsAppServiceRotas whatsAppService, MessageRepository messageRepository) {
        this.whatsAppService = whatsAppService;
        this.messageRepository = messageRepository;

    }

    @PostMapping(value = "/agendar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> agendarMensagemMultipla(
            @RequestParam("numeros") String numerosJson,
            @RequestParam("fromNumber") String fromNumber,
            @RequestParam("body") String body,
            @RequestParam(value = "horario", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horario,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            List<String> numeros = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(numerosJson, List.class);

            System.out.println("📤 Enviando campanha para: " + numeros);

            byte[] imageBytes = null;
            String imageName = null;
            String imageType = null;

            if (imageFile != null && !imageFile.isEmpty()) {
                imageBytes = imageFile.getBytes();
                imageName = imageFile.getOriginalFilename();
                imageType = imageFile.getContentType();
            }

            for (String toNumber : numeros) {
                WhatsAppMessage mensagem = new WhatsAppMessage();
                mensagem.setToNumber(toNumber);
                mensagem.setFromNumber(fromNumber);
                mensagem.setBody(body);
                mensagem.setHorario(horario);

                if (imageBytes != null) {
                    mensagem.setImage(imageBytes);
                    mensagem.setImageName(imageName);
                    mensagem.setImageType(imageType);
                }

                byte[] finalImageBytes = imageBytes;
                String finalImageName = imageName;
                String finalImageType = imageType;

                Runnable enviar = () -> {
                    try {
                        MultipartFile recreatedImage = null;
                        if (finalImageBytes != null) {
                            recreatedImage = new MultipartFile() {
                                @Override public String getName() { return "image"; }
                                @Override public String getOriginalFilename() { return finalImageName; }
                                @Override public String getContentType() { return finalImageType; }
                                @Override public boolean isEmpty() { return false; }
                                @Override public long getSize() { return finalImageBytes.length; }
                                @Override public byte[] getBytes() { return finalImageBytes; }
                                @Override public java.io.InputStream getInputStream() {
                                    return new ByteArrayInputStream(finalImageBytes);
                                }
                                @Override public void transferTo(java.io.File dest) throws IOException {
                                    java.nio.file.Files.write(dest.toPath(), finalImageBytes);
                                }
                            };
                        }

                        System.out.println("🚀 Disparando mensagem para " + toNumber);
                        whatsAppService.sendMessage("T2", toNumber, body, recreatedImage);
                        messageRepository.save(mensagem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

                if (horario != null) {
                    long delayMillis = Duration.between(LocalDateTime.now(), horario).toMillis();
                    if (delayMillis > 0) {
                        scheduler.schedule(enviar, delayMillis, TimeUnit.MILLISECONDS);
                    } else {
                        enviar.run();
                    }
                } else {
                    enviar.run();
                }
            }

            return ResponseEntity.ok("Campanha agendada/enviada para múltiplos contatos com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao processar campanha: " + e.getMessage());
        }
    }

    @PostMapping("/send/{instance}")
    public String sendMessage(
            @PathVariable String instance,
            @RequestParam String to,
            @RequestParam String message,
            @RequestParam String fromNumber,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        WhatsAppMessage msg = new WhatsAppMessage();
        msg.setFromNumber(fromNumber);
        msg.setToNumber(to);
        msg.setBody(message);

        if (image != null && !image.isEmpty()) {
            msg.setImage(image.getBytes());
            msg.setImageName(image.getOriginalFilename());
            msg.setImageType(image.getContentType());
            System.out.println("📸 Imagem recebida: " + image.getOriginalFilename());
        }

        messageRepository.save(msg);


        return whatsAppService.sendMessage(instance, to, message, image);
    }





    @GetMapping("/qrcode/{instance}")
    public String getQRcode(@PathVariable String instance){
        return whatsAppService.getQrcode(instance);
    }


    @PostMapping("/disconnect/{instance}")
    public String disconnect(@PathVariable String instance){
        return whatsAppService.disconnect(instance);
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> receberMensagem(@RequestBody WhatsAppMessage mensagem) {
        System.out.println("Mensagem recebida do WhatsApp:");
        System.out.println("De: " + mensagem.getFromNumber());
        System.out.println("Corpo: " + mensagem.getBody());
        messageRepository.save(mensagem);
        return ResponseEntity.ok("Mensagem recebida com sucesso!");
    }


    @GetMapping("/messages")
    public List<WhatsAppMessage> listarMensagens() {
        return messageRepository.findAll();
    }

    @GetMapping("/mensagens/{instancia}")
    public List<WhatsAppMessage> listarPorInstancia(@PathVariable String instancia) {
        return messageRepository.findByInstanciaIgnoreCase(instancia);
    }

    @GetMapping("/instancias")
    public List<String> listarInstancias() {
        return messageRepository.findDistinctInstancias();
    }





}

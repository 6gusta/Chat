package com.chat.gusta.controller;

import com.chat.gusta.model.WhatsAppMessage;
import com.chat.gusta.repository.MessageRepository;
import com.chat.gusta.service.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/whatsapp")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"}, allowCredentials = "true")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;
    private final MessageRepository messageRepository;

    public WhatsAppController(WhatsAppService whatsAppService, MessageRepository messageRepository) {
        this.whatsAppService = whatsAppService;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/send/{instance}")
    public String sendMessage(
            @PathVariable String instance,
            @RequestParam String to,
            @RequestParam String message,
            @RequestParam String fromNumber // <- novo parâmetro
    ) {
        // Cria objeto de mensagem
        WhatsAppMessage msg = new WhatsAppMessage();
        msg.setFromNumber(fromNumber); // agora vem do front
        msg.setToNumber(to);
        msg.setBody(message);

        // Salva no banco
        messageRepository.save(msg);

        // Envia pelo WhatsApp
        return whatsAppService.sendMessage(instance, to, message);
    }

    @GetMapping("/status/{instance}")
    public String getStatus(@PathVariable String instance){
        return whatsAppService.getStatus(instance);
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
}

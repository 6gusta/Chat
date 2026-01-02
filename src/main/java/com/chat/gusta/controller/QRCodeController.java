package com.chat.gusta.controller;


import com.chat.gusta.model.WhatsAppInstance;
import com.chat.gusta.repository.MessageRepository;

import com.chat.gusta.service.ListaTodasInstanciasService;
import com.chat.gusta.service.WhatsAppServiceRotas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    private final Map<String, String> qrspoinstancia = new ConcurrentHashMap<>();

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final WhatsAppServiceRotas whatsAppService;
    private final MessageRepository messageRepository;
    private final ListaTodasInstanciasService listaTodasIntanciasSerrvice;

    public QRCodeController(WhatsAppServiceRotas whatsAppService, MessageRepository messageRepository, ListaTodasInstanciasService listaTodasIntanciasSerrvice) {

        this.whatsAppService = whatsAppService;
        this.messageRepository = messageRepository;
        this.listaTodasIntanciasSerrvice = listaTodasIntanciasSerrvice;
    }


    @PostMapping("/receber")
    public ResponseEntity<String> receberQR(@RequestBody Map<String, Object> json) {

        String instancia = (String) json.get("instancia");
        String qr = (String) json.get("qr");


        System.out.println(" qr code da instancia " + instancia);


        enviarParaTodos(instancia, qr);

        return ResponseEntity.ok("QR recebido");
    }


    @GetMapping(value = "/stream", produces = "text/event-stream")
    public SseEmitter sseEmitter() {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.add(emitter);


        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }






    private void enviarParaTodos(String instancia, String qr) {

        for (SseEmitter emitter : emitters) {
            try {
                Map<String, Object> data = Map.of(
                        "instancia", instancia,
                        "qr", qr
                );


                emitter.send(SseEmitter.event()
                        .name("qr-update")
                        .data(data));

            } catch (Exception e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

    @GetMapping("/sync-instancias")
    public List<WhatsAppInstance> syncInstancias() {
        return listaTodasIntanciasSerrvice.sincronizarInstanciasDoNode();
    }

    @PostMapping("/instancias/salvar")
    public List<WhatsAppInstance> salvarInstancias() {
        return  listaTodasIntanciasSerrvice.sincronizarInstanciasDoNode(); // cria e salva no banco
    }


    @PostMapping("/salvar/{name}")
    public WhatsAppInstance salvar(@PathVariable String name) {
        return listaTodasIntanciasSerrvice.salvarInstanciaSimples(name);
    }





}











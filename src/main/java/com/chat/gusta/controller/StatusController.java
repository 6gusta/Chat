package com.chat.gusta.controller;

import com.chat.gusta.model.InstanceStatus;
import com.chat.gusta.model.InstanceStatusResponse;
import com.chat.gusta.service.WhatsAppServiceRotas;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class StatusController {

    private final WhatsAppServiceRotas whatsAppServiceRotas;


    public StatusController(WhatsAppServiceRotas whatsAppServiceRotas) {
        this.whatsAppServiceRotas = whatsAppServiceRotas;

    }


    @GetMapping("/status/{instance}")
    public InstanceStatus getStatus(@PathVariable String instance) {

        InstanceStatusResponse response = whatsAppServiceRotas.getStatus(instance);

        if (response == null) {
            return InstanceStatus.OFFLINE;
        }

        return response.isWhatsappReady()
                ? InstanceStatus.CONNECTED
                : InstanceStatus.DISCONNECTED;
    }
}
package com.chat.gusta.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WhatsAppMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhistorico;

    private String toNumber; // Destinatário

    @Column(name = "from_number") // mapeia o campo para a coluna "from_number"
    private String fromNumber; // Remetente

    private String content; // Texto da mensagem
    private String type; // sent / received



    private String body;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private WhatsAppInstance instance;

    // Getters e Setters
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getFromNumber() {
        return fromNumber;
    }
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }
    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }
}

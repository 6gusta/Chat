package com.chat.gusta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class MessagemPronta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long idmsg;
    private String texto;


    public MessagemPronta(long idmsg, String texto) {
        this.idmsg = idmsg;
        this.texto = texto;
    }

    public MessagemPronta() {

    }
}



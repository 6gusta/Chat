package com.chat.gusta.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class WhatsAppInstance {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long idmsg;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String status;

    @Lob
    private String sessionToken;

    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private List<WhatsAppMessage> messages;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

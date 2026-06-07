package com.chat.gusta.model;

import com.chat.gusta.model.Usuario.Cadastro;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WhatsAppMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhistorico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Cadastro usuario;

    private String toNumber; // Destinatário

    private String instancia;

    @Column(name = "from_number")
    private String fromNumber; // Remetente

    private String content; // Texto da mensagem

    private String type; // sent / received

    private LocalDateTime horario;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    private String imageName; // Nome do arquivo

    private String imageType;

    private String body;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private WhatsAppInstance instance;

    // ================= GETTERS E SETTERS =================

    public Long getIdhistorico() {
        return idhistorico;
    }

    public void setIdhistorico(Long idhistorico) {
        this.idhistorico = idhistorico;
    }

    public Cadastro getUsuario() {
        return usuario;
    }

    public void setUsuario(Cadastro usuario) {
        this.usuario = usuario;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public WhatsAppInstance getInstance() {
        return instance;
    }

    public void setInstance(WhatsAppInstance instance) {
        this.instance = instance;
    }
}
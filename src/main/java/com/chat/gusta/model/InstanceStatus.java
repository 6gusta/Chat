package com.chat.gusta.model;

public enum InstanceStatus {
    CONNECTED,      // quando a instância está conectada
    DISCONNECTED,   // quando a instância foi desconectada
    QR,             // quando precisa gerar QR code
    CONNECTING,     // quando está tentando conectar
    OFFLINE,        // quando a instância não existe ou não responde
    ERROR           // para qualquer erro inesperado
}

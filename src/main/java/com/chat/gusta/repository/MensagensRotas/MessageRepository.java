package com.chat.gusta.repository.MensagensRotas;

import com.chat.gusta.model.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<WhatsAppMessage, Long> {

    // 🔐 Buscar todas mensagens do usuário logado
    List<WhatsAppMessage> findByUsuario_IdCadastro(Long idCadastro);

    // 🔐 Buscar mensagens por usuário + instância
    List<WhatsAppMessage> findByUsuario_IdCadastroAndInstanciaIgnoreCase(
            Long idCadastro,
            String instancia
    );

    // 📌 Buscar por instância
    List<WhatsAppMessage> findByInstanciaIgnoreCase(String instancia);

    // 📌 Listar instâncias existentes
    @Query("SELECT DISTINCT m.instancia FROM WhatsAppMessage m")
    List<String> findDistinctInstancias();
}
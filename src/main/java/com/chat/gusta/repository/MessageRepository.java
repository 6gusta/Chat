package com.chat.gusta.repository;

import com.chat.gusta.model.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<WhatsAppMessage, Long> {

    List<WhatsAppMessage> findByInstanciaIgnoreCase(String instancia);
    @Query("SELECT DISTINCT m.instancia FROM WhatsAppMessage m")
    List<String> findDistinctInstancias();



}

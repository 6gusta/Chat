package com.chat.gusta.repository;

import com.chat.gusta.model.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<WhatsAppMessage, Long> {


}

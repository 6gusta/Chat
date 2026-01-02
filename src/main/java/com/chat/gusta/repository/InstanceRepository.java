package com.chat.gusta.repository;

import com.chat.gusta.model.WhatsAppInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstanceRepository extends JpaRepository<WhatsAppInstance, Long> {

    Optional<WhatsAppInstance> findByName(String name); // usa "name" que é único
}

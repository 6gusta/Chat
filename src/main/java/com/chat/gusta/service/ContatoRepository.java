package com.chat.gusta.service;

import com.chat.gusta.model.Contatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contatos, Long> {
    boolean existsByNumero(String numero);
}

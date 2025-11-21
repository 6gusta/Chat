package com.chat.gusta.repository;

import com.chat.gusta.model.Contatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contatos, Long> {
    boolean existsByNumero(String numero);
}

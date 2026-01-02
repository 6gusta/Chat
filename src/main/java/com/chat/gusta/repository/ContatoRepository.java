package com.chat.gusta.repository;

import com.chat.gusta.model.Contatos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contatos, Long> {
    boolean existsByNumero(String numero);
    List<Contatos> findByInstancia(String instancia);


}

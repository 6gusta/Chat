package com.chat.gusta.repository.Cadastro;

import com.chat.gusta.model.Usuario.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro,Long> {

    boolean existsByNome(String nome);
    boolean existsByRole(String role);

    Optional<Cadastro> findByNome(String nome);

    Optional<Cadastro> findByEmail(String email);
}

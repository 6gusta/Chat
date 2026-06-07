package com.chat.gusta.model.Usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCadastro;
    private String nome;
    private String senha;
    private String role;
    @Column(unique = true, nullable = false)
    private String email;

    private String empresa;
}

package com.chat.gusta.service.User;

import com.chat.gusta.Exceptions.PermissaoInvalidaException;
import com.chat.gusta.model.Usuario.Cadastro;
import com.chat.gusta.repository.Cadastro.CadastroRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CadastroUserService {
     private final CadastroRepository cadastroRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastroUserService(CadastroRepository cadastroRepository, PasswordEncoder passwordEncoder) {
        this.cadastroRepository = cadastroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cadastro cadastro(
            String nome,
            String senha,
            String role,
            String email,
            String empresa) {

        long totalUsuarios = cadastroRepository.count();

        Cadastro cadastro = new Cadastro();
        cadastro.setNome(nome);
        cadastro.setSenha(passwordEncoder.encode(senha));
        cadastro.setRole(role);
        cadastro.setEmail(email);
        cadastro.setEmpresa(empresa);


        return cadastroRepository.save(cadastro);
    }


}

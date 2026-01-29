package com.chat.gusta.service.User;

import com.chat.gusta.Exceptions.PermissaoInvalidaException;
import com.chat.gusta.model.Usuario.LoginDTO;
import com.chat.gusta.model.Usuario.Cadastro;
import com.chat.gusta.repository.Cadastro.CadastroRepository;
import com.chat.gusta.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class LoginServiceAdmin {

    private final CadastroRepository cadastroRepository;
    private static final Logger LOGGER =
            Logger.getLogger(LoginServiceAdmin.class.getName());

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginServiceAdmin(
            CadastroRepository cadastroRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {

        this.cadastroRepository = cadastroRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String autenticar(LoginDTO loginDTO) {

        Cadastro cadastroAdmin = cadastroRepository
                .findByNome(loginDTO.getNome())
                .orElseThrow(() ->
                        new PermissaoInvalidaException("Usuário não encontrado"));

        if (!passwordEncoder.matches(
                loginDTO.getSenha(),
                cadastroAdmin.getSenha())) {

            throw new PermissaoInvalidaException("Senha incorreta");
        }

        String token = jwtUtil.generateToken(
                cadastroAdmin.getIdCadastro(), // ID
                cadastroAdmin.getNome(),
                cadastroAdmin.getRole()
        );

        LOGGER.info("Login bem-sucedido para " + cadastroAdmin.getNome());

        return token;
    }
}

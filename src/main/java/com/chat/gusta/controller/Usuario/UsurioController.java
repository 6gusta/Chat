package com.chat.gusta.controller.Usuario;

import com.chat.gusta.model.Usuario.Cadastro;
import com.chat.gusta.repository.Cadastro.CadastroRepository;
import com.chat.gusta.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/conta")
public class UsurioController {

    private final CadastroRepository cadastroRepository;
    private final JwtUtil jwtUtil;

    public UsurioController(CadastroRepository cadastroRepository, JwtUtil jwtUtil) {
        this.cadastroRepository = cadastroRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public ResponseEntity<Cadastro> me() {

        Cadastro usuario = (Cadastro) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(usuario);
    }



}

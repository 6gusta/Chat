package com.chat.gusta.controller.Usuario;


import com.chat.gusta.model.Usuario.Cadastro;
import com.chat.gusta.repository.Cadastro.CadastroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PrimeiroADminController {

    private final CadastroRepository repository;
    private final PasswordEncoder encoder;

    public PrimeiroADminController(CadastroRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    @PostConstruct
    public void seedAdmin() {

        boolean existeAdmin = repository.existsByRole("ADMIN");

        if (!existeAdmin) {
            Cadastro admin = new Cadastro();
            admin.setNome("Admin Master");
            admin.setEmail("admin@admin.com");
            admin.setEmpresa("Sistema");
            admin.setRole("ADMIN");
            admin.setSenha(encoder.encode("123456"));

            repository.save(admin);

            System.out.println("✅ Admin padrão criado");
        }
    }

}

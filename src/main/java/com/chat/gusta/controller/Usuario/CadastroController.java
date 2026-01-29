package com.chat.gusta.controller.Usuario;


import com.chat.gusta.model.Usuario.Cadastro;
import com.chat.gusta.service.User.CadastroUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Cadastro")
public class CadastroController {

    private final CadastroUserService cadastroUserService;

    public CadastroController(CadastroUserService cadastroUserService) {
        this.cadastroUserService = cadastroUserService;
    }

    @PostMapping("Registro")
    public ResponseEntity<Cadastro> CadastroUsaer(@RequestBody Cadastro cadastro){

        Cadastro cadastro1 = cadastroUserService.cadastro(
                cadastro.getNome(),
                cadastro.getSenha(),
                cadastro.getRole(),
                cadastro.getEmail(),
                cadastro.getEmpresa()
        );

        return ResponseEntity.ok(cadastro1);
    }
}

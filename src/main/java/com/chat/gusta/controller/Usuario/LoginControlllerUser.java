package com.chat.gusta.controller.Usuario;


import com.chat.gusta.Exceptions.PermissaoInvalidaException;
import com.chat.gusta.model.Usuario.LoginDTO;
import com.chat.gusta.service.User.LoginServiceAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginControlllerUser {
    private  final LoginServiceAdmin loginService;

    public LoginControlllerUser(LoginServiceAdmin loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            String token = loginService.autenticar(loginDTO);
            return ResponseEntity.ok(token);

        } catch (PermissaoInvalidaException.UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");

        } catch (PermissaoInvalidaException.SenhaIncorretaException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");

        } catch (PermissaoInvalidaException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Permissão inválida para este usuário");

        } catch (Exception e) {
            e.printStackTrace(); // Para depuração
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}



package com.chat.gusta.controller;


import com.chat.gusta.model.Contatos;
import com.chat.gusta.repository.ContatoRepository;
import com.chat.gusta.service.ContatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
@CrossOrigin(origins = "http://localhost:4200")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    // ✅ SALVAR CONTATO COM INSTÂNCIA
    @PostMapping
    public Contatos salvar(@RequestBody Contatos contato) {
        return contatoService.salvarContato(
                contato.getNome(),
                contato.getNumero(),
                contato.getInstancia()
        );
    }

    // ✅ LISTAR TODOS (corrige o erro 405)
    @GetMapping
    public List<Contatos> listarTodos() {
        return contatoService.listarTodos();
    }

    // ✅ LISTAR POR INSTÂNCIA
    @GetMapping("/instancia/{instancia}")
    public List<Contatos> listarPorInstancia(@PathVariable String instancia) {
        return contatoService.listarPorInstancia(instancia);
    }
}

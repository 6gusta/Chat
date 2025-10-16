package com.chat.gusta.controller;


import com.chat.gusta.model.Contatos;
import com.chat.gusta.service.ContatoRepository;
import com.chat.gusta.service.ContatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService contatoService;
    private final ContatoRepository contatoRepository;

    public ContatoController(ContatoService contatoService, ContatoRepository contatoRepository) {
        this.contatoService = contatoService;
        this.contatoRepository = contatoRepository;
    }

    @PostMapping
    public Contatos adicionacontatos(@RequestBody Contatos contatos) {

       return contatoService.salvarcontato(contatos.getNome(), contatos.getNumero());
    }

    @GetMapping()
    public List<Contatos> listaContatos() {
        return  contatoRepository.findAll();
    }
}

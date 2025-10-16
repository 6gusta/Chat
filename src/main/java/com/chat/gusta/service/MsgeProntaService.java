package com.chat.gusta.service;


import com.chat.gusta.model.MessagemPronta;
import com.chat.gusta.repository.ProntaReposiotory;
import org.springframework.stereotype.Service;
import com.chat.gusta.repository.AdicionamsgeRepository;

import java.util.List;

@Service
public class MsgeProntaService {

    private final ProntaReposiotory prontaReposiotory; // mensagens fixas
    private final AdicionamsgeRepository repository;    // mensagens do banco

    public MsgeProntaService(ProntaReposiotory prontaReposiotory, AdicionamsgeRepository repository) {
        this.prontaReposiotory = prontaReposiotory;
        this.repository = repository;
    }

    // Lista todas as mensagens (fixas + banco)
    public List<MessagemPronta> listaMessages() {
        List<MessagemPronta> fixas = prontaReposiotory.findAll();
        List<MessagemPronta> adicionadas = repository.findAll();

        List<MessagemPronta> todas = new java.util.ArrayList<>();
        todas.addAll(fixas);
        todas.addAll(adicionadas);

        return todas;
    }


    public MessagemPronta buscaporId(Long idmsg) {
        return repository.findById(idmsg)
                .orElseGet(() -> prontaReposiotory.findById(idmsg));
    }

    // Adiciona mensagem nova no banco
    public MessagemPronta adicionar(String texto) {
        MessagemPronta mensagem = new MessagemPronta();
        mensagem.setTexto(texto);
        return repository.save(mensagem);
    }

    // Deleta mensagem apenas do banco
    public boolean delProduto(Long idmsg) {
        if (!repository.existsById(idmsg)) return false;
        repository.deleteById(idmsg);
        return true;
    }
}

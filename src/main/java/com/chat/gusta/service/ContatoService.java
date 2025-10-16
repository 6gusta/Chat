package com.chat.gusta.service;


import com.chat.gusta.model.Contatos;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {


    private final  ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contatos salvarcontato(String nome, String numero) {
        if (contatoRepository.existsByNumero(numero)){
            throw  new RuntimeException(" contato ja existe ");
        }

        Contatos contatos = new Contatos();
        contatos.setNumero(nome);
        contatos.setNome(numero);
        return  contatoRepository.save(contatos);
    }


}

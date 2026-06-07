package com.chat.gusta.service.Contatos;

import com.chat.gusta.model.Contatos;
import com.chat.gusta.repository.Contatos.ContatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contatos salvarContato(String nome, String numero, String instancia) {

        if (contatoRepository.existsByNumero(numero)) {
            throw new RuntimeException("Contato já existe");
        }

        Contatos contato = new Contatos();
        contato.setNome(nome);           // ✅ CERTO
        contato.setNumero(numero);       // ✅ CERTO
        contato.setInstancia(instancia); // ✅ VINCULA INSTÂNCIA

        return contatoRepository.save(contato);
    }

    public List<Contatos> listarTodos() {
        return contatoRepository.findAll();
    }

    public List<Contatos> listarPorInstancia(String instancia) {
        return contatoRepository.findByInstancia(instancia);
    }
}

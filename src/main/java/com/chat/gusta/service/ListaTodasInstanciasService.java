package com.chat.gusta.service;

import com.chat.gusta.model.InstanceStatus;
import com.chat.gusta.model.InstanciaDTO;
import com.chat.gusta.model.WhatsAppInstance;
import com.chat.gusta.repository.InstanceRepository;
import com.chat.gusta.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ListaTodasInstanciasService {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;
    private final InstanceRepository instanceRepository;
    private final String baseUrl = "http://localhost:3000";

    public ListaTodasInstanciasService(MessageRepository messageRepository,
                                       RestTemplate restTemplate,
                                       InstanceRepository instanceRepository) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
        this.instanceRepository = instanceRepository;
    }

    // ----------------------
    // Buscar todas instâncias do banco
    // ----------------------
    public List<WhatsAppInstance> buscarTodasInstancias() {
        return instanceRepository.findAll();
    }

    // ----------------------
    // Buscar instância específica por nome
    // ----------------------
    public Optional<WhatsAppInstance> buscarPorNome(String nome) {
        return instanceRepository.findByName(nome);
    }

    // ----------------------
    // Salvar uma instância nova se não existir
    // ----------------------
    public WhatsAppInstance salvarInstancia(String nome) {
        return instanceRepository.findByName(nome)
                .orElseGet(() -> {
                    WhatsAppInstance nova = new WhatsAppInstance();
                    nova.setName(nome);
                    nova.setStatus(InstanceStatus.OFFLINE);
                    nova.setCreatedAt(LocalDateTime.now());
                    nova.setUpdatedAt(LocalDateTime.now());
                    return instanceRepository.save(nova);
                });
    }

    // ----------------------
    // Buscar instâncias do Node e salvar/atualizar no banco
    // ----------------------
    @Transactional
    public List<WhatsAppInstance> sincronizarInstanciasDoNode() {
        ResponseEntity<InstanciaDTO[]> response = restTemplate.getForEntity(
                baseUrl + "/instancias", InstanciaDTO[].class
        );
        InstanciaDTO[] nodeInstancias = response.getBody();

        List<WhatsAppInstance> instanciasSalvas = new ArrayList<>();

        if (nodeInstancias != null) {
            for (InstanciaDTO dto : nodeInstancias) {
                WhatsAppInstance inst = instanceRepository.findByName(dto.getName())
                        .orElseGet(() -> {
                            WhatsAppInstance nova = new WhatsAppInstance();
                            nova.setName(dto.getName());
                            nova.setStatus(InstanceStatus.OFFLINE);
                            nova.setCreatedAt(LocalDateTime.now());
                            return nova; // ainda não salva aqui
                        });

                inst.setUpdatedAt(LocalDateTime.now());
                instanceRepository.save(inst); // salva/atualiza só uma vez
                instanciasSalvas.add(inst);
            }
        }

        return instanciasSalvas;
    }

    public WhatsAppInstance salvarInstanciaSimples(String name) {
        WhatsAppInstance inst = new WhatsAppInstance();
        inst.setName(name);
        inst.setStatus(InstanceStatus.OFFLINE);

        // Salva imediatamente no banco
        return instanceRepository.saveAndFlush(inst);
    }
}

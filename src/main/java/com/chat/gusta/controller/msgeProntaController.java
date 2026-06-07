package com.chat.gusta.controller;


import com.chat.gusta.model.MessagemPronta;
import com.chat.gusta.repository.MsgeProntas.MsgeProntasRepository;
import com.chat.gusta.repository.MensagensRotas.MessageRepository;
import com.chat.gusta.repository.MsgeProntas.ProntaReposiotory;
import com.chat.gusta.service.MsgProntas.MsgeProntaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mensagens")
public class msgeProntaController {

    private  final MsgeProntaService msgeProntaService;
    private final MsgeProntasRepository msgeProntasRepository;

    public msgeProntaController(MsgeProntaService msgeProntaService, ProntaReposiotory reposiotory, MessageRepository repository, MsgeProntasRepository msgeProntasRepository) {
        this.msgeProntaService = msgeProntaService;
        this.msgeProntasRepository = msgeProntasRepository;


    }

    @GetMapping
    public List<MessagemPronta> listartodas(){
        return msgeProntaService.listaMessages();
    }

    @GetMapping("/{idmsge}")
    public MessagemPronta buscaporId(@PathVariable Long idmsge){

        return msgeProntaService.buscaporId(idmsge);

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<MessagemPronta> cadastrar(@RequestBody MessagemPronta messagemPronta){
        MessagemPronta salva = msgeProntasRepository.save(messagemPronta);
        return ResponseEntity.ok(salva);
    }


    @DeleteMapping("/msge/{idmsge}")
    public ResponseEntity<?> deletar(@PathVariable Long idmsge) {
        Boolean sucesso = msgeProntaService.delProduto(idmsge);

        if (sucesso) {
            return ResponseEntity.ok(Map.of("mensagem", "Produto deletado com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Produto não encontrado"));
        }
    }




}

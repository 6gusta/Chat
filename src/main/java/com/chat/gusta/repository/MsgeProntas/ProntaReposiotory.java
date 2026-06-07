package com.chat.gusta.repository.MsgeProntas;

import com.chat.gusta.model.MessagemPronta;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public class ProntaReposiotory {

    private final List<MessagemPronta> mensagens = List.of(
            new MessagemPronta(1L, "Olá, tudo bem? 😊"),
            new MessagemPronta(2L, "Estamos analisando seu pedido, em breve retornamos!"),
            new MessagemPronta(3L, "Obrigado pelo contato! 💬"),
            new MessagemPronta(4L, "Seu atendimento foi finalizado, tenha um ótimo dia! 🌟")
    );

    public List<MessagemPronta> findAll() {
        return mensagens;
    }

    public MessagemPronta findById(long id) {
        return mensagens.stream()
                .filter(m -> m.getIdmsg() == id)
                .findFirst()
                .orElse(null);

    }
}

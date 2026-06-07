package com.chat.gusta.repository.MsgeProntas;

import com.chat.gusta.model.MessagemPronta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MsgeProntasRepository extends JpaRepository<MessagemPronta, Long> {


}

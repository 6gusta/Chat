package com.chat.gusta.repository;

import com.chat.gusta.model.MessagemPronta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdicionamsgeRepository extends JpaRepository<MessagemPronta, Long> {


}

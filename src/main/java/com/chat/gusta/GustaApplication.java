package com.chat.gusta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GustaApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        System.out.println("🕐 Timezone definido: " + TimeZone.getDefault().getID());

        SpringApplication.run(GustaApplication.class, args);
    }
}

package com.chat.gusta;

import com.chat.gusta.service.RotasApi.WhatsAppServiceRotas;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatTest {


    private String invokeFormatNumber(WhatsAppServiceRotas service, String numero) {
        try {
            Method method = WhatsAppServiceRotas.class.getDeclaredMethod("formatNumber", String.class);
            method.setAccessible(true);
            return (String) method.invoke(service, numero);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao invocar formatNumber: " + e.getMessage(), e);
        }
    }



    @Test
    void deveAdicionarDDIBrasilQuandoFaltar() {
        WhatsAppServiceRotas service = new WhatsAppServiceRotas(null);

        String resultado = invokeFormatNumber(service, "61988887777");

        assertEquals("5561988887777@c.us", resultado);
    }

    @Test
    void deveAdicionarDDIEQuandoNumeroNaoComecarCom55() {
        WhatsAppServiceRotas service = new WhatsAppServiceRotas(null);
        String resultado = invokeFormatNumber(service, "988887777");
        assertEquals("55988887777@c.us", resultado);


    }


    @Test
    void deveManterNumeroComDDI55() {
        WhatsAppServiceRotas service = new WhatsAppServiceRotas(null);
        String resultado = invokeFormatNumber(service, "5561988887777");
        assertEquals("5561988887777@c.us", resultado);
    }

    @Test
    void deveRemoverCaracteresNaoNumericos() {
        WhatsAppServiceRotas service = new WhatsAppServiceRotas(null);
        String resultado = invokeFormatNumber(service, "(61) 98888-7777");
        assertEquals("5561988887777@c.us", resultado);
    }
}

package com.chat.gusta;

import com.chat.gusta.model.MessagemPronta;
import com.chat.gusta.repository.AdicionamsgeRepository;
import com.chat.gusta.repository.ProntaReposiotory;
import com.chat.gusta.service.MsgeProntaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MsgeProntaServiceTest {

    @Mock
    private ProntaReposiotory prontaReposiotory;

    @Mock
    private AdicionamsgeRepository adicionamsgeRepository;

    private MsgeProntaService msgeProntaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        msgeProntaService = new MsgeProntaService(prontaReposiotory, adicionamsgeRepository);
    }

    @Test
    void deveListarTodasMensagens(){

        MessagemPronta fixa1 = new MessagemPronta();
        fixa1.setIdmsg(1L);
        fixa1.setTexto("Fixa 1");

        MessagemPronta fixa2 = new MessagemPronta();
        fixa2.setIdmsg(2L);
        fixa2.setTexto("Fixa 2");

        MessagemPronta adicionada = new MessagemPronta();
        adicionada.setIdmsg(3L);
        adicionada.setTexto("Adicionada 1");

        when(prontaReposiotory.findAll()).thenReturn(Arrays.asList(fixa1, fixa2));
        when(adicionamsgeRepository.findAll()).thenReturn(List.of(adicionada));

        List<MessagemPronta> todas = msgeProntaService.listaMessages();

        assertEquals(3, todas.size());
        assertTrue(todas.contains(fixa1));
        assertTrue(todas.contains(fixa2));
        assertTrue(todas.contains(adicionada));

        verify(prontaReposiotory, times(1)).findAll();
        verify(adicionamsgeRepository, times(1)).findAll();
    }


    @Test
    void deveBuscarPorId_noBanco(){
        MessagemPronta fixa1 = new MessagemPronta();
        fixa1.setIdmsg(1L);
        fixa1.setTexto("Do Banco");

        when(adicionamsgeRepository.findById(1L)).thenReturn(Optional.of(fixa1));

        MessagemPronta resultado = msgeProntaService.buscaporId(1L);
        assertEquals("Do Banco", resultado.getTexto());
        verify(adicionamsgeRepository, times(1)).findById(1L);
        verifyNoInteractions(prontaReposiotory);

    }

    @Test
    void deveBuscarPorId_nasFixas_quandoNaoExisteNoBanco() {
        MessagemPronta fixa = new MessagemPronta();
        fixa.setIdmsg(2L);
        fixa.setTexto("Fixa");

        when(adicionamsgeRepository.findById(2L)).thenReturn(Optional.empty());
        when(prontaReposiotory.findById(2L)).thenReturn(fixa);

        MessagemPronta resultado = msgeProntaService.buscaporId(2L);

        assertEquals("Fixa", resultado.getTexto());
        verify(adicionamsgeRepository, times(1)).findById(2L);
        verify(prontaReposiotory, times(1)).findById(2L);
    }

    @Test
    void deveAdicionarMensagem() {
        MessagemPronta nova = new MessagemPronta();
        nova.setTexto("Nova mensagem");

        when(adicionamsgeRepository.save(any(MessagemPronta.class))).thenReturn(nova);

        MessagemPronta resultado = msgeProntaService.adicionar("Nova mensagem");

        assertEquals("Nova mensagem", resultado.getTexto());
        verify(adicionamsgeRepository, times(1)).save(any());
    }

    @Test
    void deveDeletarMensagem_quandoExisteNoBanco() {

        when(adicionamsgeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(adicionamsgeRepository).deleteById(1L);

        boolean resultado = msgeProntaService.delProduto(1L);
        assertTrue(resultado);
        verify(adicionamsgeRepository, times(1)).existsById(1L);
        verify(adicionamsgeRepository, times(1)).deleteById(1L);




    }

    @Test
    void naoDeveDeletarMensagem_quandoNaoExiste() {
        when(adicionamsgeRepository.existsById(1L)).thenReturn(false);

        boolean resultado = msgeProntaService.delProduto(1L);

        assertFalse(resultado);
        verify(adicionamsgeRepository, times(1)).existsById(1L);
        verify(adicionamsgeRepository, never()).deleteById(anyLong());
    }
    }



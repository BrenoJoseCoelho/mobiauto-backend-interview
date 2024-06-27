package com.example.mobiautobackendinterview;

import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.repository.RevendaRepository;
import com.example.mobiautobackendinterview.service.RevendaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RevendaServiceIntegrationTest {

    @Autowired
    private RevendaService revendaService;

    @Autowired
    private RevendaRepository revendaRepository;

    @Test
    public void salvar_DeveSalvarRevenda() {
        Revenda revenda = new Revenda();
        revenda.setCnpj("12345678000100");
        revenda.setNomeSocial("Revenda Teste");

        Revenda revendaSalva = revendaService.salvar(revenda);

        assertNotNull(revendaSalva.getId());
        assertEquals("12345678000100", revendaSalva.getCnpj());
        assertEquals("Revenda Teste", revendaSalva.getNomeSocial());
    }

    @Test
    public void findById_DeveRetornarRevendaQuandoEncontrada() {
        Revenda revenda = new Revenda();
        revenda.setCnpj("12345678000100");
        revenda.setNomeSocial("Revenda Teste");

        Revenda revendaSalva = revendaService.salvar(revenda);

        Revenda revendaEncontrada = revendaService.findById(revendaSalva.getId());

        assertNotNull(revendaEncontrada);
        assertEquals(revendaSalva.getId(), revendaEncontrada.getId());
    }

    @Test
    public void findById_DeveLancarExcecaoQuandoNaoEncontrada() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            revendaService.findById(999L);
        });

        String expectedMessage = "Revenda não encontrada";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
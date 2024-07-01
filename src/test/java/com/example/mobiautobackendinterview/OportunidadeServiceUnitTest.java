package com.example.mobiautobackendinterview;

import com.example.mobiautobackendinterview.entity.Oportunidade;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;
import com.example.mobiautobackendinterview.repository.OportunidadeRepository;
import com.example.mobiautobackendinterview.repository.UsuarioRepository;
import com.example.mobiautobackendinterview.service.OportunidadeService;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OportunidadeServiceUnitTest {

    @InjectMocks
    private OportunidadeService oportunidadeService;

    @Mock
    private OportunidadeRepository oportunidadeRepository;

    @Mock(lenient = true)
    private RevendaService revendaService;

    @Mock(lenient = true)
    private UsuarioService usuarioService;

    @Mock(lenient = true)
    private UsuarioRepository usuarioRepository;

    @Test
    public void criarOportunidade_DeveSalvarOportunidade() {
        // Mock Revenda
        Revenda revenda = new Revenda();
        revenda.setId(1L);
        revenda.setCnpj("12345678000100");
        revenda.setNomeSocial("Revenda Teste");

        // Mock Usuario (Responsável)
        Usuario responsavel = new Usuario();
        responsavel.setId(2L);
        responsavel.setNome("Responsável Teste");

        // Mock Oportunidade
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setNomeCliente("Cliente Teste");
        oportunidade.setRevenda(revenda);
        oportunidade.setResponsavel(responsavel);
        oportunidade.setStatus(StatusOportunidade.NOVO);

        when(revendaService.findById(1L)).thenReturn(revenda);
        when(usuarioService.buscarPorId(2L)).thenReturn(responsavel);
        when(oportunidadeRepository.save(any(Oportunidade.class))).thenReturn(oportunidade);

        // Executa o método de criar
        Oportunidade novaOportunidade = oportunidadeService.criarOportunidade(oportunidade);

        // Verifica se a oportunidade foi salva corretamente
        assertNotNull(novaOportunidade);
        assertEquals("Cliente Teste", novaOportunidade.getNomeCliente());
        assertEquals("Revenda Teste", novaOportunidade.getRevenda().getNomeSocial());
        assertEquals("Responsável Teste", novaOportunidade.getResponsavel().getNome());
    }

    @Test
    public void criarOportunidade_DeveLancarExcecaoQuandoRevendaNaoEncontrada() {
        // Mock Oportunidade
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setNomeCliente("Cliente Teste");
        oportunidade.setRevenda(new Revenda());
        oportunidade.getRevenda().setId(1L);

        when(revendaService.findById(1L)).thenThrow(new IllegalArgumentException("Revenda não encontrada"));

        // Verifica se a exceção é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            oportunidadeService.criarOportunidade(oportunidade);
        });

        assertEquals("Revenda não encontrada", exception.getMessage());
    }

    @Test
    public void criarOportunidade_DeveLancarExcecaoQuandoResponsavelNaoEncontrado() {
        // Mock Revenda
        Revenda revenda = new Revenda();
        revenda.setId(1L);

        // Mock Oportunidade com responsável null
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setNomeCliente("Cliente Teste");
        oportunidade.setRevenda(revenda);
        oportunidade.setResponsavel(new Usuario());
        oportunidade.getResponsavel().setId(2L);

        when(revendaService.findById(1L)).thenReturn(revenda);
        when(usuarioService.buscarPorId(2L)).thenReturn(null); // Simula o caso em que o responsável não é encontrado

        // Verifica se a exceção é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            oportunidadeService.criarOportunidade(oportunidade);
        });

        assertEquals("Responsável não encontrado", exception.getMessage());
    }
}

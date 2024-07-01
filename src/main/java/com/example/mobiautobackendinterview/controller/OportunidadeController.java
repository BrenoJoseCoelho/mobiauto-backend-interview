package com.example.mobiautobackendinterview.controller;

import com.example.mobiautobackendinterview.dto.OportunidadeDTO;
import com.example.mobiautobackendinterview.entity.Oportunidade;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.repository.OportunidadeRepository;
import com.example.mobiautobackendinterview.service.OportunidadeService;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/oportunidades")
public class OportunidadeController {

    @Autowired
    private OportunidadeService oportunidadeService;
    @Autowired
    private OportunidadeRepository oportunidaderepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RevendaService revendaService;

    @PostMapping
    public ResponseEntity<Oportunidade> criarOportunidade(@Valid @RequestBody OportunidadeDTO oportunidadeDTO) {
        // Validar a existência da revenda
        Revenda revenda = revendaService.findById(oportunidadeDTO.getRevendaId());
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda não encontrada");
        }

        // Busca o responsável
        Usuario responsavel = usuarioService.buscarPorId(oportunidadeDTO.getResponsavelId());
        if (responsavel == null) {
            throw new IllegalArgumentException("Responsável não encontrado");
        }

        // Construir a oportunidade a partir do DTO
        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setRevenda(revenda);
        oportunidade.setResponsavel(responsavel);
        oportunidade.setStatus(oportunidadeDTO.getStatus());
        oportunidade.setNomeCliente(oportunidadeDTO.getNomeCliente());
        oportunidade.setEmailCliente(oportunidadeDTO.getEmailCliente());
        oportunidade.setTelefoneCliente(oportunidadeDTO.getTelefoneCliente());
        oportunidade.setMarcaVeiculo(oportunidadeDTO.getMarcaVeiculo());
        oportunidade.setModeloVeiculo(oportunidadeDTO.getModeloVeiculo());
        oportunidade.setVersaoVeiculo(oportunidadeDTO.getVersaoVeiculo());
        oportunidade.setAnoModeloVeiculo(oportunidadeDTO.getAnoModeloVeiculo());
        oportunidade.setStatusConclusao(oportunidadeDTO.getStatusConclusao());
        oportunidade.setMotivoConclusao(oportunidadeDTO.getMotivoConclusao());
        oportunidade.setDataAtribuicao(oportunidadeDTO.getDataAtribuicao());
        oportunidade.setDataConclusao(oportunidadeDTO.getDataConclusao());

        // Salvar a oportunidade
        Oportunidade novaOportunidade = oportunidadeService.criarOportunidade(oportunidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOportunidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editarOportunidade(@PathVariable Long id,
                                                                 @Valid @RequestBody OportunidadeDTO oportunidadeDTO) {
        // Buscar a oportunidade existente pelo ID
        Optional<Oportunidade> oportunidadeOptional = oportunidaderepository.findById(id);
        if (oportunidadeOptional == null) {
            throw new EntityNotFoundException("Oportunidade com o ID " + id + " não encontrada");
        }
        Oportunidade oportunidadeExistente = oportunidadeOptional.get();
        // Validar a existência da revenda
        Revenda revenda = revendaService.findById(oportunidadeDTO.getRevendaId());
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + oportunidadeDTO.getRevendaId() + " não encontrada");
        }

        // Validar a existência do usuário responsável
        Usuario responsavel = usuarioService.buscarPorId(oportunidadeDTO.getResponsavelId());
        if (responsavel == null) {
            throw new IllegalArgumentException("Usuário com o ID " + oportunidadeDTO.getResponsavelId() + " não encontrado");
        }

        // Aplicar as alterações da oportunidadeDTO na oportunidadeExistente
        oportunidadeExistente.setRevenda(revenda);
        oportunidadeExistente.setResponsavel(responsavel);
        oportunidadeExistente.setStatus(oportunidadeDTO.getStatus());
        oportunidadeExistente.setNomeCliente(oportunidadeDTO.getNomeCliente());
        oportunidadeExistente.setEmailCliente(oportunidadeDTO.getEmailCliente());
        oportunidadeExistente.setTelefoneCliente(oportunidadeDTO.getTelefoneCliente());
        oportunidadeExistente.setMarcaVeiculo(oportunidadeDTO.getMarcaVeiculo());
        oportunidadeExistente.setModeloVeiculo(oportunidadeDTO.getModeloVeiculo());
        oportunidadeExistente.setVersaoVeiculo(oportunidadeDTO.getVersaoVeiculo());
        oportunidadeExistente.setAnoModeloVeiculo(oportunidadeDTO.getAnoModeloVeiculo());
        oportunidadeExistente.setStatusConclusao(oportunidadeDTO.getStatusConclusao());
        oportunidadeExistente.setMotivoConclusao(oportunidadeDTO.getMotivoConclusao());
        oportunidadeExistente.setDataAtribuicao(oportunidadeDTO.getDataAtribuicao());
        oportunidadeExistente.setDataConclusao(oportunidadeDTO.getDataConclusao());

        // Salvar a oportunidade atualizada
        Oportunidade oportunidadeAtualizada = oportunidadeService.editarOportunidade(oportunidadeExistente, responsavel);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/atribuir/{responsavelId}")
    public ResponseEntity<Void> atribuirResponsavel(@PathVariable Long id,
                                                            @PathVariable Long responsavelId) {
        Usuario responsavel = usuarioService.buscarPorId(responsavelId);
        if (responsavel == null) {
            throw new IllegalArgumentException("Usuário com o ID " + responsavelId + " não encontrado");
        }
        Oportunidade oportunidade = oportunidadeService.atribuirResponsavel(id, responsavel.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/revenda/{revendaId}")
    public ResponseEntity<List<Oportunidade>> listarOportunidades(@PathVariable Long revendaId,
                                                                  @RequestParam StatusOportunidade status) {
        Revenda revenda = revendaService.findById(revendaId);
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + revendaId + " não encontrada");
        }
        List<Oportunidade> oportunidades = oportunidadeService.listarPorRevendaEStatus(revenda, status);
        return ResponseEntity.ok(oportunidades);
    }
    @PutMapping("/{id}/concluir")
    public ResponseEntity<Void> concluirOportunidade(@PathVariable Long id,
                                                                   @RequestParam String motivoConclusao) {
        Oportunidade oportunidade = oportunidadeService.concluirOportunidade(id, motivoConclusao);
        return ResponseEntity.ok().build();
    }

}
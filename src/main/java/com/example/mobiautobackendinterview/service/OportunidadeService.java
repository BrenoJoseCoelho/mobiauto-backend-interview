package com.example.mobiautobackendinterview.service;

import com.example.mobiautobackendinterview.entity.*;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;
import com.example.mobiautobackendinterview.repository.OportunidadeRepository;
import com.example.mobiautobackendinterview.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepository oportunidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RevendaService revendaService;

    @Autowired
    private UsuarioService usuarioService;

    public Oportunidade criarOportunidade(Oportunidade oportunidade) {
        Long revendaId = oportunidade.getRevenda().getId();
        Long responsavelId = (oportunidade.getResponsavel() != null) ? oportunidade.getResponsavel().getId() : null;

        // Valida e busca a Revenda
        Revenda revenda = revendaService.findById(revendaId);
        oportunidade.setRevenda(revenda);

        // Valida e busca o Responsável
        Usuario responsavel = null;
        if (responsavelId != null) {
            responsavel = usuarioService.buscarPorId(responsavelId);
        }
        if (responsavel == null) {
            throw new IllegalArgumentException("Responsável não encontrado");
        }
        oportunidade.setResponsavel(responsavel);

        // Salva a Oportunidade
        return oportunidadeRepository.save(oportunidade);
    }

    public Oportunidade editarOportunidade(Oportunidade oportunidade, Usuario usuario) {
        if (usuario.getPerfil() == Perfil.ADMIN ||
                usuario.getPerfil() == Perfil.PROPRIETARIO ||
                usuario.getPerfil() == Perfil.GERENTE ||
                oportunidade.getResponsavel().equals(usuario)) {
            return oportunidadeRepository.save(oportunidade);
        } else {
            throw new AccessDeniedException("Permissão negada para editar a oportunidade");
        }
    }

    public Oportunidade atribuirResponsavel(Long oportunidadeId, Long responsavelId) {
        Oportunidade oportunidade = oportunidadeRepository.findById(oportunidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Oportunidade com ID " + oportunidadeId + " não encontrada"));

        // Valida e busca o Responsável
        Usuario responsavel = null;
        if (responsavelId != null) {
            responsavel = usuarioService.buscarPorId(responsavelId);
        }
        if (responsavel == null) {
            throw new IllegalArgumentException("Responsável não encontrado");
        }
        oportunidade.setResponsavel(responsavel);
        return oportunidadeRepository.save(oportunidade);
    }

    public List<Oportunidade> listarPorRevendaEStatus(Revenda revenda, StatusOportunidade status) {
        return oportunidadeRepository.findByRevendaAndStatus(revenda, status);
    }

    public void distribuirOportunidades() {
        List<Usuario> assistentes = usuarioRepository.findUsuariosWithOportunidadesByPerfil(Perfil.ASSISTENTE);
        List<Oportunidade> oportunidadesNaoAtribuidas = oportunidadeRepository.findByResponsavelAndStatus(null, StatusOportunidade.NOVO);

        PriorityQueue<Usuario> filaAssistentes = new PriorityQueue<>(Comparator.comparingInt(u -> {
            int oportunidadesEmAndamento = u.getOportunidades() != null ? u.getOportunidades().size() : 0;
            LocalDateTime ultimaAtribuicao = u.getUltimaAtribuicao();
            long segundosDesdeUltimaAtribuicao = ultimaAtribuicao != null ?
                    LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ultimaAtribuicao.toEpochSecond(ZoneOffset.UTC) : 0;

            return Math.toIntExact(oportunidadesEmAndamento + segundosDesdeUltimaAtribuicao);
        }));

        for (Usuario assistente : assistentes) {
            filaAssistentes.add(assistente);
        }

        for (Oportunidade oportunidade : oportunidadesNaoAtribuidas) {
            if (!filaAssistentes.isEmpty()) {
                Usuario assistente = filaAssistentes.poll();
                oportunidade.setResponsavel(assistente);
                oportunidade.setDataAtribuicao(LocalDateTime.now());
                oportunidade.setStatus(StatusOportunidade.EM_ATENDIMENTO);
                oportunidadeRepository.save(oportunidade);

                List<Oportunidade> oportunidadesDoAssistente = assistente.getOportunidades();
                if (oportunidadesDoAssistente == null) {
                    oportunidadesDoAssistente = new ArrayList<>();
                }
                oportunidadesDoAssistente.add(oportunidade);
                assistente.setOportunidades(oportunidadesDoAssistente);
                usuarioRepository.save(assistente);

                assistente.setUltimaAtribuicao(LocalDateTime.now());
                filaAssistentes.add(assistente);
            } else {
                throw new IllegalStateException("Nenhum assistente disponível");
            }
        }
    }
    @Transactional
    public Oportunidade concluirOportunidade(Long oportunidadeId, String motivoConclusao) {
        Oportunidade oportunidade = oportunidadeRepository.findById(oportunidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Oportunidade não encontrada"));

        oportunidade.setStatus(StatusOportunidade.CONCLUIDO);
        oportunidade.setMotivoConclusao(motivoConclusao);
        oportunidade.setDataConclusao(LocalDateTime.now());

        return oportunidadeRepository.save(oportunidade);
    }

}
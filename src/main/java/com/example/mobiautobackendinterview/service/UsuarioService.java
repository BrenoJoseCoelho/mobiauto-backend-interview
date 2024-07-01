package com.example.mobiautobackendinterview.service;

import com.example.mobiautobackendinterview.entity.CustomUserDetails;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.exception.UnauthorizedException;
import com.example.mobiautobackendinterview.repository.RevendaRepository;
import com.example.mobiautobackendinterview.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RevendaRepository revendaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RevendaService revendaService;

    public Usuario salvar(Usuario usuario, Long revendaId, CustomUserDetails userDetails) {
        Revenda revenda = revendaService.findById(revendaId);
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + revendaId + " não encontrada");
        }


        usuario.setRevenda(revenda);
        if (usuario.getSenha() != null) {
            String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCodificada);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado, CustomUserDetails userDetails) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente == null) {
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado");
        }


        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());
        usuarioExistente.setUltimaAtribuicao(usuarioAtualizado.getUltimaAtribuicao());

        return usuarioRepository.save(usuarioExistente);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        }
        return false;
    }

    private boolean isOwnerOfRevenda(CustomUserDetails userDetails, Revenda revenda) {
        if (userDetails == null || userDetails.getUsuario() == null || revenda == null) {
            return false;
        }

        Usuario usuario = userDetails.getUsuario();
        return usuario.getPerfil() == Perfil.PROPRIETARIO && usuario.getRevenda() != null &&
                usuario.getRevenda().getId().equals(revenda.getId());
    }
}
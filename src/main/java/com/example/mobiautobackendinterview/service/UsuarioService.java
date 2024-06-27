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



    public Usuario salvar(Usuario usuario, Long revendaId) {

        Revenda revenda = revendaService.findById(revendaId);
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + revendaId + " não encontrada");
        }

        usuario.setRevenda(revenda);

        return usuarioRepository.save(usuario);
    }


    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    // Outros métodos conforme necessidade

    private boolean isAdmin(CustomUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isOwnerOrManager(CustomUserDetails userDetails, Long revendaId) {
        // Verificar se o usuário é proprietário ou gerente da revenda com o ID fornecido
        if (userDetails == null || userDetails.getUsuario() == null) {
            return false;
        }

        Usuario usuario = userDetails.getUsuario();

        // Verificar se o perfil do usuário é Proprietário ou Gerente
        if (usuario.getPerfil() == Perfil.PROPRIETARIO || usuario.getPerfil() == Perfil.GERENTE) {
            // Verificar se o usuário está associado à revenda com o ID fornecido
            if (usuario.getRevenda() != null && usuario.getRevenda().getId() != null &&
                    usuario.getRevenda().getId().equals(revendaId)) {
                return true;
            }
        }

        return false;
    }
}
package com.example.mobiautobackendinterview.controller;


import com.example.mobiautobackendinterview.dto.UsuarioDTO;
import com.example.mobiautobackendinterview.entity.CustomUserDetails;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.mobiautobackendinterview.exception.UnauthorizedException;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RevendaService revendaService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROPRIETARIO') or principal.username == 'admin'")
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(
            @RequestBody UsuarioDTO usuarioDTO,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {

        // Conversão do DTO para a entidade Usuario
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        String senhaCodificada = passwordEncoder.encode(usuarioDTO.getSenha());
        usuario.setSenha(senhaCodificada);
        usuario.setPerfil(Perfil.valueOf(usuarioDTO.getPerfil()));
        usuario.setUltimaAtribuicao(usuarioDTO.getUltimaAtribuicao());

        // Busca da revenda associada
        Revenda revenda = revendaService.findById(usuarioDTO.getRevendaId());
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + usuarioDTO.getRevendaId() + " não encontrada");
        }
        usuario.setRevenda(revenda);

        // Salvando o usuário
        Usuario novoUsuario = usuarioService.salvar(usuario, usuarioDTO.getRevendaId(), userDetails);
        return ResponseEntity.ok(novoUsuario);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROPRIETARIO') or principal.username == 'admin'")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioDTO usuarioDTO,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {

        Usuario usuarioExistente = usuarioService.buscarPorId(id);

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os campos do usuário existente com os dados do DTO
        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSenha(usuarioDTO.getSenha());
        usuarioExistente.setPerfil(Perfil.valueOf(usuarioDTO.getPerfil()));
        usuarioExistente.setUltimaAtribuicao(usuarioDTO.getUltimaAtribuicao());

        // Chama o serviço para atualizar o usuário
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id,usuarioExistente, userDetails);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    private boolean isAdmin(CustomUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
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
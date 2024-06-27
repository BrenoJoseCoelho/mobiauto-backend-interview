package com.example.mobiautobackendinterview.controller;


import com.example.mobiautobackendinterview.dto.UsuarioDTO;
import com.example.mobiautobackendinterview.entity.CustomUserDetails;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        String nome = usuarioDTO.getNome();
        String email = usuarioDTO.getEmail();
        String senha = usuarioDTO.getSenha();
        String perfil = usuarioDTO.getPerfil();
        Long revendaId = usuarioDTO.getRevendaId();
        LocalDateTime ultimaAtribuicao = usuarioDTO.getUltimaAtribuicao();

        // Criar objetos a partir dos parâmetros recebidos
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setPerfil(Perfil.valueOf(perfil));
        usuario.setUltimaAtribuicao(ultimaAtribuicao);

        // Carregar a Revenda pelo ID
        Revenda revenda = revendaService.findById(revendaId);
        if (revenda == null) {
            throw new IllegalArgumentException("Revenda com o ID " + revendaId + " não encontrada");
        }

        usuario.setRevenda(revenda);

        // Salvar o usuário
        Usuario novoUsuario = usuarioService.salvar(usuario, revendaId);
        return ResponseEntity.ok(novoUsuario);
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

        // Verificar se o usuário é proprietário da revenda
        return usuario.getPerfil() == Perfil.PROPRIETARIO && usuario.getRevenda() != null &&
                usuario.getRevenda().getId().equals(revenda.getId());
    }
}
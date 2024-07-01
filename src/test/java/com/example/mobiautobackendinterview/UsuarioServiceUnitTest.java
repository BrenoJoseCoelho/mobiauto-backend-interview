package com.example.mobiautobackendinterview;

import com.example.mobiautobackendinterview.entity.CustomUserDetails;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.repository.UsuarioRepository;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceUnitTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RevendaService revendaService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void salvar_DeveSalvarUsuarioComRevenda() {
        // Mock Revenda
        Revenda revenda = new Revenda();
        revenda.setId(1L);
        revenda.setCnpj("12345678000100");
        revenda.setNomeSocial("Revenda Teste");

        // Mock Usuario
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senha123");
        usuario.setPerfil(Perfil.ADMIN);
        usuario.setRevenda(revenda);

        // Mock CustomUserDetails
        Usuario adminUsuario = new Usuario();
        adminUsuario.setPerfil(Perfil.ADMIN);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        CustomUserDetails adminDetails = new CustomUserDetails(adminUsuario, authorities);

        when(revendaService.findById(1L)).thenReturn(revenda);
        when(passwordEncoder.encode("senha123")).thenReturn("senha123Encoded");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Executa o método de salvar
        Usuario usuarioSalvo = usuarioService.salvar(usuario, 1L, adminDetails);

        // Verifica se o usuário foi salvo corretamente
        assertNotNull(usuarioSalvo);
        assertEquals("Teste", usuarioSalvo.getNome());
        assertEquals("teste@teste.com", usuarioSalvo.getEmail());
        assertEquals("Revenda Teste", usuarioSalvo.getRevenda().getNomeSocial());
    }

    @Test
    public void salvar_DeveLancarExcecaoQuandoRevendaNaoEncontrada() {
        // Mock Usuario
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senha123");
        usuario.setPerfil(Perfil.ADMIN);

        // Mock CustomUserDetails
        Usuario adminUsuario = new Usuario();
        adminUsuario.setPerfil(Perfil.ADMIN);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        CustomUserDetails adminDetails = new CustomUserDetails(adminUsuario, authorities);

        when(revendaService.findById(1L)).thenReturn(null);

        // Verifica se a exceção é lançada
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.salvar(usuario, 1L, adminDetails);
        });
    }
}

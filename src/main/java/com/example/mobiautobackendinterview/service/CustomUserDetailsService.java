package com.example.mobiautobackendinterview.service;

import com.example.mobiautobackendinterview.entity.CustomUserDetails;
import com.example.mobiautobackendinterview.entity.Usuario;
import com.example.mobiautobackendinterview.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return buildCustomUserDetails(usuario);
    }

    private CustomUserDetails buildCustomUserDetails(Usuario usuario) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        // Adicione as permissões/grupos do usuário, se aplicável
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(usuario, authorities);
    }
}

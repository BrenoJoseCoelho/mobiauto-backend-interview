package com.example.mobiautobackendinterview.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private Usuario usuario;
    private Set<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario, Set<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // Outros métodos getters conforme necessidade

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementar lógica se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementar lógica se necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementar lógica se necessário
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementar lógica se necessário
    }
}

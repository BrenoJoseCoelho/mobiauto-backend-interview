package com.example.mobiautobackendinterview.dto;

import java.time.LocalDateTime;

public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String perfil;
    private Long revendaId;
    private LocalDateTime ultimaAtribuicao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Long getRevendaId() {
        return revendaId;
    }

    public void setRevendaId(Long revendaId) {
        this.revendaId = revendaId;
    }

    public LocalDateTime getUltimaAtribuicao() {
        return ultimaAtribuicao;
    }

    public void setUltimaAtribuicao(LocalDateTime ultimaAtribuicao) {
        this.ultimaAtribuicao = ultimaAtribuicao;
    }
}
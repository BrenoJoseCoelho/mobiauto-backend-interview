package com.example.mobiautobackendinterview.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados para criação de um novo usuário")
public class UsuarioDTO {

    @Schema(description = "Nome do usuário", example = "João da Silva", required = true)
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@example.com", required = true)
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123", required = true)
    private String senha;

    @Schema(description = "Perfil do usuário", example = "ADMIN", required = true)
    private String perfil;

    @Schema(description = "ID da revenda associada", example = "1", required = true)
    private Long revendaId;

    @Schema(description = "Data e hora da última atribuição", example = "2024-07-01T12:35:12.804", required = true)
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
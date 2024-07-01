package com.example.mobiautobackendinterview.entity;
import com.example.mobiautobackendinterview.enuns.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Usuario {
    @OneToMany(mappedBy = "responsavel")
    @JsonIgnore
    private List<Oportunidade> oportunidades;
    public List<Oportunidade> getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(List<Oportunidade> oportunidades) {
        this.oportunidades = oportunidades;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "revenda_id")
    private Revenda revenda;

    private LocalDateTime ultimaAtribuicao;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public LocalDateTime getUltimaAtribuicao() {
        return ultimaAtribuicao;
    }

    public void setUltimaAtribuicao(LocalDateTime ultimaAtribuicao) {
        this.ultimaAtribuicao = ultimaAtribuicao;
    }
}
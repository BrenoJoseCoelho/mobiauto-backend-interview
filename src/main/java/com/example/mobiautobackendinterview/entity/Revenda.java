package com.example.mobiautobackendinterview.entity;
import com.example.mobiautobackendinterview.util.CnpjValido;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Revenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    @Column(unique = true, nullable = false)
    private String cnpj;

    @NotBlank(message = "Nome social é obrigatório")
    @Column(nullable = false)
    private String nomeSocial;

    // Getters e Setters

    public String getCnpj() {
        return cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    // Outros getters e setters
}
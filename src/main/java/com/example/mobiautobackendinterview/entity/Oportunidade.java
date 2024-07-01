package com.example.mobiautobackendinterview.entity;

import com.example.mobiautobackendinterview.enuns.StatusConclusao;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "oportunidades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "responsavel"})
public class Oportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenda_id", nullable = false)
    private Revenda revenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOportunidade status = StatusOportunidade.NOVO;

    @NotBlank(message = "Nome do cliente é obrigatório")
    private String nomeCliente;

    @Email(message = "Email do cliente deve ser válido")
    @NotBlank(message = "Email do cliente é obrigatório")
    private String emailCliente;

    @NotBlank(message = "Telefone do cliente é obrigatório")
    private String telefoneCliente;

    @NotBlank(message = "Marca do veículo é obrigatório")
    private String marcaVeiculo;

    @NotBlank(message = "Modelo do veículo é obrigatório")
    private String modeloVeiculo;

    @NotBlank(message = "Versão do veículo é obrigatório")
    private String versaoVeiculo;

    @NotBlank(message = "Ano modelo do veículo é obrigatório")
    private String anoModeloVeiculo;

    @Enumerated(EnumType.STRING)
    private StatusConclusao statusConclusao;

    private String motivoConclusao;

    private LocalDateTime dataAtribuicao;

    private LocalDateTime dataConclusao;

    // Construtores

    public Oportunidade() {
    }

    public Oportunidade(Revenda revenda, Usuario responsavel, String nomeCliente, String emailCliente,
                        String telefoneCliente, String marcaVeiculo, String modeloVeiculo, String versaoVeiculo,
                        String anoModeloVeiculo) {
        this.revenda = revenda;
        this.responsavel = responsavel;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.telefoneCliente = telefoneCliente;
        this.marcaVeiculo = marcaVeiculo;
        this.modeloVeiculo = modeloVeiculo;
        this.versaoVeiculo = versaoVeiculo;
        this.anoModeloVeiculo = anoModeloVeiculo;
        this.status = StatusOportunidade.NOVO;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public StatusOportunidade getStatus() {
        return status;
    }

    public void setStatus(StatusOportunidade status) {
        this.status = status;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public String getVersaoVeiculo() {
        return versaoVeiculo;
    }

    public void setVersaoVeiculo(String versaoVeiculo) {
        this.versaoVeiculo = versaoVeiculo;
    }

    public String getAnoModeloVeiculo() {
        return anoModeloVeiculo;
    }

    public void setAnoModeloVeiculo(String anoModeloVeiculo) {
        this.anoModeloVeiculo = anoModeloVeiculo;
    }

    public StatusConclusao getStatusConclusao() {
        return statusConclusao;
    }

    public void setStatusConclusao(StatusConclusao statusConclusao) {
        this.statusConclusao = statusConclusao;
    }

    public String getMotivoConclusao() {
        return motivoConclusao;
    }

    public void setMotivoConclusao(String motivoConclusao) {
        this.motivoConclusao = motivoConclusao;
    }

    public LocalDateTime getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(LocalDateTime dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
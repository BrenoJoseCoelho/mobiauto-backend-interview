package com.example.mobiautobackendinterview.dto;

import com.example.mobiautobackendinterview.enuns.StatusConclusao;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;

import java.time.LocalDateTime;

public class OportunidadeDTO {

    private Long revendaId;
    private Long responsavelId;
    private StatusOportunidade status;
    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private String versaoVeiculo;
    private String anoModeloVeiculo;
    private StatusConclusao statusConclusao;
    private String motivoConclusao;
    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataConclusao;

    // Getters and setters

    public Long getRevendaId() {
        return revendaId;
    }

    public void setRevendaId(Long revendaId) {
        this.revendaId = revendaId;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
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

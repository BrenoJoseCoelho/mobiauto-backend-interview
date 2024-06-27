package com.example.mobiautobackendinterview.service;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.repository.RevendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevendaService {

    @Autowired
    private RevendaRepository revendaRepository;

    public Revenda salvar(Revenda revenda) {
        validarCnpjUnico(revenda.getCnpj());
        return revendaRepository.save(revenda);
    }
    public Revenda findById(Long id) {
        return revendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Revenda não encontrada"));
    }
    public List<Revenda> listarTodas() {
        return revendaRepository.findAll();
    }

    private void validarCnpjUnico(String cnpj) {
        if (revendaRepository.findByCnpj(cnpj).isPresent()) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
    }
}
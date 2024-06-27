package com.example.mobiautobackendinterview.controller;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.service.RevendaService;
import com.example.mobiautobackendinterview.util.CnpjValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revendas")
public class RevendaController {

    @Autowired
    private RevendaService revendaService;

    @PostMapping
    public ResponseEntity<Object> criarRevenda(@RequestParam String nomeSocial, @RequestParam String cnpj) {

        String cnpjSemCaracteresEspeciais = cnpj.replaceAll("[^0-9]", "");

        Revenda novaRevenda = new Revenda();
        novaRevenda.setNomeSocial(nomeSocial);
        novaRevenda.setCnpj(cnpjSemCaracteresEspeciais);

        Revenda savedRevenda = revendaService.salvar(novaRevenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRevenda);
    }

    @GetMapping
    public ResponseEntity<List<Revenda>> listarRevendas() {
        List<Revenda> revendas = revendaService.listarTodas();
        return ResponseEntity.ok(revendas);
    }
}
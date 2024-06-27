package com.example.mobiautobackendinterview.repository;

import com.example.mobiautobackendinterview.entity.Oportunidade;
import com.example.mobiautobackendinterview.entity.Revenda;
import com.example.mobiautobackendinterview.enuns.StatusOportunidade;
import com.example.mobiautobackendinterview.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
    List<Oportunidade> findByRevendaAndStatus(Revenda revenda, StatusOportunidade status);
    List<Oportunidade> findByResponsavelAndStatus(Usuario responsavel, StatusOportunidade status);
}
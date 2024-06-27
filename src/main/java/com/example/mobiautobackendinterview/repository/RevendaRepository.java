package com.example.mobiautobackendinterview.repository;

import com.example.mobiautobackendinterview.entity.Revenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RevendaRepository extends JpaRepository<Revenda, Long> {
    Optional<Revenda> findByCnpj(String cnpj);
}
package com.example.mobiautobackendinterview.repository;

import com.example.mobiautobackendinterview.enuns.Perfil;
import com.example.mobiautobackendinterview.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.oportunidades WHERE u.perfil = :perfil")
    List<Usuario> findUsuariosWithOportunidadesByPerfil(@Param("perfil") Perfil perfil);

}
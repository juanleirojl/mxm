package com.mxm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mxm.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}

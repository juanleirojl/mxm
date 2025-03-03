package com.mxm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mxm.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Optional<Perfil> findByNome(String nome);
}

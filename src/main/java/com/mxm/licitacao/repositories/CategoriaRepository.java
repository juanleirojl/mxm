package com.mxm.licitacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxm.licitacao.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
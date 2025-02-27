package com.mxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mxm.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}

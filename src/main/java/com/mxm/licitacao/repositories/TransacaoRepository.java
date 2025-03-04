package com.mxm.licitacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxm.licitacao.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	List<Transacao> findByLicitacaoId(Long licitacaoId);
}
package com.mxm.licitacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.enums.TipoTransacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	List<Transacao> findByLicitacaoIdOrderByCriadoEmDesc(Long licitacaoId);

	@Query("SELECT t FROM Transacao t WHERE t.licitacao.id = :licitacaoId AND t.tipoTransacao = :tipo")
    List<Transacao> findByLicitacaoIdAndTipo(@Param("licitacaoId") Long licitacaoId, @Param("tipo") TipoTransacao tipo);

	List<Transacao> findAllByOrderByCriadoEmDesc();

	boolean existsByCategoriaId(Long categoriaId);

	boolean existsByLicitacaoId(Long licitacaoId);
}
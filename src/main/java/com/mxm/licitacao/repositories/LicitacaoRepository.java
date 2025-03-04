package com.mxm.licitacao.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mxm.licitacao.dto.FluxoMensalDTO;
import com.mxm.licitacao.dto.FornecedorGastoDTO;
import com.mxm.licitacao.dto.MetodoPagamentoDTO;
import com.mxm.licitacao.dto.ReceitaFonteDTO;
import com.mxm.licitacao.dto.SaldoAtualDTO;
import com.mxm.licitacao.dto.SaldoMensalDTO;
import com.mxm.licitacao.dto.TipoGastoDTO;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.enums.TipoTransacao;

@Repository
public interface LicitacaoRepository extends JpaRepository<Licitacao, Long> {
    Optional<Licitacao> findByNome(String nome);

    // Busca todas as licitações cujos nomes estão na lista fornecida
    List<Licitacao> findByNomeInIgnoreCase(Set<String> nomes);
    
    
    @Query("SELECT new com.mxm.licitacao.dto.FornecedorGastoDTO(t.origemDestino, SUM(t.valor)) " +
            "FROM Transacao t WHERE t.licitacao.id = :licitacaoId AND t.tipoTransacao = :tipo " +
            "GROUP BY t.origemDestino ORDER BY SUM(t.valor) DESC")
     List<FornecedorGastoDTO> findTopFornecedoresByLicitacaoId(@Param("licitacaoId") Long licitacaoId, 
                                                               @Param("tipo") TipoTransacao tipo);
    
    @Query("SELECT new com.mxm.licitacao.dto.SaldoAtualDTO(l.saldoAtual) FROM Licitacao l WHERE l.id = :licitacaoId")
    SaldoAtualDTO getResumoFinanceiro(@Param("licitacaoId") Long licitacaoId);

    @Query("""
    	    SELECT new com.mxm.licitacao.dto.SaldoMensalDTO(
    	        CAST(YEAR(t.data) AS String),
    	        CAST(MONTH(t.data) AS String),
    	        SUM(t.valor)
    	    )
    	    FROM Transacao t
    	    WHERE t.licitacao.id = :licitacaoId
    	    GROUP BY YEAR(t.data), MONTH(t.data)
    	    ORDER BY YEAR(t.data), MONTH(t.data)
    	""")
    	List<SaldoMensalDTO> getSaldoMensal(@Param("licitacaoId") Long licitacaoId);


    @Query("""
    	    SELECT new com.mxm.licitacao.dto.FluxoMensalDTO(
    	        CAST(YEAR(t.data) AS String),
    	        CAST(MONTH(t.data) AS String),
    	        SUM(CASE WHEN t.tipoTransacao = 'ENTRADA' THEN t.valor ELSE 0 END),
    	        SUM(CASE WHEN t.tipoTransacao = 'SAIDA' THEN t.valor ELSE 0 END)
    	    )
    	    FROM Transacao t
    	    WHERE t.licitacao.id = :licitacaoId
    	    GROUP BY YEAR(t.data), MONTH(t.data)
    	    ORDER BY YEAR(t.data), MONTH(t.data)
    	""")
    	List<FluxoMensalDTO> getEntradasVsSaidas(@Param("licitacaoId") Long licitacaoId);

    @Query("SELECT new com.mxm.licitacao.dto.TipoGastoDTO(t.categoria.nome, SUM(t.valor)) " +
           "FROM Transacao t WHERE t.licitacao.id = :licitacaoId AND t.tipoTransacao = 'SAIDA' " +
           "GROUP BY t.categoria.nome")
    List<TipoGastoDTO> getTiposGasto(@Param("licitacaoId") Long licitacaoId);

    @Query("""
    	    SELECT new com.mxm.licitacao.dto.MetodoPagamentoDTO(
    	        CAST(t.metodoPagamento AS string),
    	        COALESCE(SUM(t.valor), 0)
    	    ) 
    	    FROM Transacao t 
    	    WHERE t.licitacao.id = :licitacaoId 
    	    GROUP BY t.metodoPagamento
    	""")
    	List<MetodoPagamentoDTO> getMetodosPagamento(@Param("licitacaoId") Long licitacaoId);


    @Query("SELECT new com.mxm.licitacao.dto.ReceitaFonteDTO(t.origemDestino, SUM(t.valor)) " +
           "FROM Transacao t WHERE t.licitacao.id = :licitacaoId AND t.tipoTransacao = 'ENTRADA' " +
           "GROUP BY t.origemDestino")
    List<ReceitaFonteDTO> getReceitaPorFonte(@Param("licitacaoId") Long licitacaoId);

    @Query("SELECT new com.mxm.licitacao.dto.FornecedorGastoDTO(t.origemDestino, SUM(t.valor)) " +
           "FROM Transacao t WHERE t.licitacao.id = :licitacaoId AND t.tipoTransacao = 'SAIDA' " +
           "GROUP BY t.origemDestino ORDER BY SUM(t.valor) DESC")
    List<FornecedorGastoDTO> getFornecedoresMaisPagos(@Param("licitacaoId") Long licitacaoId);
}

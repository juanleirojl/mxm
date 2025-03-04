package com.mxm.licitacao.services;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.dto.ReceitaFonteDTO;
import com.mxm.licitacao.dto.VendasMensaisDTO;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.enums.TipoTransacao;
import com.mxm.licitacao.repositories.TransacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicitacaoRelatorioService {

    private final TransacaoRepository transacaoRepository;

    /**
     * 📌 Obtém os gastos agrupados por categoria para exibição no gráfico.
     */
    public List<ReceitaFonteDTO> getGastosPorCategoria(Long licitacaoId) {
        return transacaoRepository.findByLicitacaoIdAndTipo(licitacaoId, TipoTransacao.SAIDA)
                .stream()
                .collect(Collectors.groupingBy(
                        Transacao::getOrigemDestino,
                        Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add)
                ))
                .entrySet()
                .stream()
                .map(entry -> new ReceitaFonteDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 📌 Obtém os gastos ao longo do tempo para exibição no gráfico.
     */
    public List<VendasMensaisDTO> getGastosPorData(Long licitacaoId) {
        return transacaoRepository.findByLicitacaoIdAndTipo(licitacaoId, TipoTransacao.SAIDA)
                .stream()
                .collect(Collectors.groupingBy(
                        transacao -> YearMonth.from(transacao.getData()),
                        Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add)
                ))
                .entrySet()
                .stream()
                .map(entry -> new VendasMensaisDTO(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 📌 Obtém as receitas agrupadas por fonte para exibição no gráfico.
     */
    public List<ReceitaFonteDTO> getReceitaPorFonte(Long licitacaoId) {
        return transacaoRepository.findByLicitacaoIdAndTipo(licitacaoId, TipoTransacao.ENTRADA)
                .stream()
                .collect(Collectors.groupingBy(
                        Transacao::getOrigemDestino,
                        Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add)
                ))
                .entrySet()
                .stream()
                .map(entry -> new ReceitaFonteDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 📌 Obtém as vendas mensais para exibição no gráfico.
     */
    public List<VendasMensaisDTO> getVendasMensais(Long licitacaoId) {
        return transacaoRepository.findByLicitacaoIdAndTipo(licitacaoId, TipoTransacao.ENTRADA)
                .stream()
                .collect(Collectors.groupingBy(
                        transacao -> YearMonth.from(transacao.getData()),
                        Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add)
                ))
                .entrySet()
                .stream()
                .map(entry -> new VendasMensaisDTO(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

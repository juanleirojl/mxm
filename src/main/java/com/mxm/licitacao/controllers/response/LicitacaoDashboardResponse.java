package com.mxm.licitacao.controllers.response;

import java.math.BigDecimal;
import java.util.List;

import com.mxm.licitacao.dto.CategoriaGastoDTO;
import com.mxm.licitacao.dto.FluxoCaixaDTO;
import com.mxm.licitacao.dto.TransacaoResumoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LicitacaoDashboardResponse {
    private String nomeLicitacao;
    private BigDecimal saldoAtual;
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
    private BigDecimal lucroPrejuizo;
    private int totalTransacoes;
    private List<TransacaoResumoDTO> ultimasTransacoes;
    private List<CategoriaGastoDTO> categoriasMaisGastas;
    private List<FluxoCaixaDTO> fluxoCaixa;
}

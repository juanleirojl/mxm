package com.mxm.licitacao.controllers.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicitacaoResponse {
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal saldoAtual;
    private BigDecimal totalEntradas; // 🟢 NOVO CAMPO
    private BigDecimal totalSaidas; // 🔴 NOVO CAMPO
}

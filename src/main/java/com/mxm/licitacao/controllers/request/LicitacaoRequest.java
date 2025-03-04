package com.mxm.licitacao.controllers.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicitacaoRequest {
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal saldoAtual;
}
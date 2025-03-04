package com.mxm.licitacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FluxoCaixaDTO {
    private LocalDate data;
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
}

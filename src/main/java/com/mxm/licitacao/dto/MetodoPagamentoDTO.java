package com.mxm.licitacao.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagamentoDTO {
    private String metodo;
    private BigDecimal total;
}

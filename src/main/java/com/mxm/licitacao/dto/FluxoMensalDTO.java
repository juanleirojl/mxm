package com.mxm.licitacao.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FluxoMensalDTO {
    private String mes;
    private String ano;
    private BigDecimal entradas;
    private BigDecimal saidas;
}

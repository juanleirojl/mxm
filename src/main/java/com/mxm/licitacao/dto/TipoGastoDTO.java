package com.mxm.licitacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TipoGastoDTO {
    private String categoria;
    private BigDecimal total;
}

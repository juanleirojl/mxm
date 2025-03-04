package com.mxm.licitacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class FornecedorGastoDTO {
    private String fornecedor;
    private BigDecimal totalPago;
}

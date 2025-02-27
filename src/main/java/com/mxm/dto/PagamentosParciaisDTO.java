package com.mxm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentosParciaisDTO {
    private String cliente;
    private BigDecimal totalPago;
    private BigDecimal valorRestante;
}

package com.mxm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasPorMesDTO {
    private String mes;
    private String ano;
    private Long quantidade;
    private BigDecimal totalVendas;
}

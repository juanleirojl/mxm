package com.mxm.licitacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class VendasMensaisDTO {
	private String mesAno; // Exemplo: "2025-03"
    private BigDecimal totalVendas;
}

package com.mxm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoResponse {
	private Long id;
	private BigDecimal valorEmprestado;
    private BigDecimal valorPago;
    private LocalDate dataEmprestimo;
    private String observacao;
    
    public BigDecimal getSaldoDevedor() {
        return valorEmprestado.subtract(valorPago != null ? valorPago : BigDecimal.ZERO);
    }
}

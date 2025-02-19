package com.mxm.dto;

import java.math.BigDecimal;

import com.mxm.enums.StatusPagamento;
import com.mxm.enums.StatusPedido;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoRequest {

    @NotNull
    private Long cimentoId;
    
    @NotNull
    private Long clienteId;
    
    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal precoCimentoComprado;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal precoCimentoVendido;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal frete;
    
    private BigDecimal valorParcial;
    
    private StatusPedido statusPedido;
    
    private StatusPagamento statusPagamento;
    
    @NotEmpty
    private String data;
}

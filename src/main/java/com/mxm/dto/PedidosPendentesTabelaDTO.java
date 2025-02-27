package com.mxm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 🔹 Necessário para o Hibernate
public class PedidosPendentesTabelaDTO {
    private String cliente;
    private BigDecimal totalAPagar;
    private BigDecimal valorPendente;
    private Integer diasAtraso; // 🔹 Deve ser Integer
    
    public PedidosPendentesTabelaDTO(String cliente, BigDecimal totalAPagar, BigDecimal valorPendente, Integer diasAtraso) {
        this.cliente = cliente;
        this.totalAPagar = totalAPagar;
        this.valorPendente = valorPendente;
        this.diasAtraso = diasAtraso;
    }
}

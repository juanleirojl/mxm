package com.mxm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidosPendentesDTO {
    private String cliente;
    private Long quantidadePedidos;
    private BigDecimal totalPendente;

}

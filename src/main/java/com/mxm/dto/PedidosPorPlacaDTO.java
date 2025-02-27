package com.mxm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidosPorPlacaDTO {
    private String placa;
    private Long quantidadePedidos;
}

package com.mxm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidosPorMotoristaDTO {
    private String motorista;
    private Long quantidadePedidos;
}

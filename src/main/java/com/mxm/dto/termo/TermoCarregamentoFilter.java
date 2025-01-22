package com.mxm.dto.termo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TermoCarregamentoFilter {

    private String motorista;
    private String placa;
    private Long numeroPedido;
    private Long quantidadeSacos;
    private String data;
    
}

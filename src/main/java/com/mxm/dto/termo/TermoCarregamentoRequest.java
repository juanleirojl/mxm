package com.mxm.dto.termo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TermoCarregamentoRequest {

    @NotEmpty
    private String motorista;
    @NotEmpty
    private String placa;
    @NotEmpty
    private Long numeroPedido;
    @NotEmpty
    private Long quantidadeSacos;
    @NotEmpty
    private String data;
    
}

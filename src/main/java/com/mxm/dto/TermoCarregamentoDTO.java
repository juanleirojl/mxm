package com.mxm.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermoCarregamentoDTO {
    private Long id;
    private String motorista;
    private String placa;
    private Long quantidadeSacos;
    private Long numeroPedido;
    private LocalDateTime dataCadastro;
}

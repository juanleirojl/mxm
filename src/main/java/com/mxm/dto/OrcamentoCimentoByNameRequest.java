package com.mxm.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoCimentoByNameRequest {
  private String empresa;       // Nome da empresa
  private String cimento;      // Marca do cimento
  private String cidade;        // Nome da cidade
  private String cliente;       // Nome do cliente (opcional)
  private Integer quantidadeSacos;  // Quantidade de sacos
  private BigDecimal valorUnitario; // Valor unitário
  private BigDecimal valorFrete;    // Valor do frete (opcional)
}

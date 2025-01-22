package com.mxm.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrecoCimentoRequest {
  @NotNull
  private Long empresaId;
  @NotNull
  private Long cimentoId;
  @NotNull
  private Long cidadeId;
  @NotNull
  private BigDecimal preco280;
  @NotNull
  private BigDecimal preco560;
  @NotNull
  private BigDecimal preco720;
  
}

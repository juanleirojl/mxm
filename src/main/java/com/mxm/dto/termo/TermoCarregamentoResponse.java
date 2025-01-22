package com.mxm.dto.termo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TermoCarregamentoResponse {
  private String empresa;
  private String cnpj;
  private String cidade;
  private String tipoMarca;
  private BigDecimal preco280;
  private BigDecimal preco560;
  private BigDecimal preco720;

  public TermoCarregamentoResponse(String empresa, String cnpj, String cidade, String tipoMarca,
      BigDecimal preco280, BigDecimal preco560, BigDecimal preco720) {
    this.empresa = empresa;
    this.cnpj = cnpj;
    this.cidade = cidade;
    this.tipoMarca = tipoMarca;
    this.preco280 = preco280;
    this.preco560 = preco560;
    this.preco720 = preco720;
  }
}

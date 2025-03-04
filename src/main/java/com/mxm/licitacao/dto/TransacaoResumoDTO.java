package com.mxm.licitacao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mxm.licitacao.enums.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransacaoResumoDTO {
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private LocalDateTime data;
}

package com.mxm.licitacao.controllers.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mxm.licitacao.enums.MetodoPagamento;
import com.mxm.licitacao.enums.TipoTransacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoResponse {
    private Long id;
    private LocalDateTime data;
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private String categoriaNome;
    private String licitacaoNome;
    private String origemDestino;
    private MetodoPagamento metodoPagamento;
    private String responsavelNome;
}
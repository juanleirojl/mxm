package com.mxm.licitacao.controllers.request;

import java.math.BigDecimal;

import com.mxm.licitacao.enums.MetodoPagamento;
import com.mxm.licitacao.enums.TipoTransacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequest {
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private Long categoriaId;
    private Long licitacaoId;
    private String origemDestino;
    private MetodoPagamento metodoPagamento;
    private Long responsavelId;
}
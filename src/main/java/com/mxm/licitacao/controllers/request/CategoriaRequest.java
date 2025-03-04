package com.mxm.licitacao.controllers.request;

import com.mxm.licitacao.enums.TipoTransacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {
    private String nome;
    private TipoTransacao tipoTransacao;
    private Long categoriaPaiId;
}
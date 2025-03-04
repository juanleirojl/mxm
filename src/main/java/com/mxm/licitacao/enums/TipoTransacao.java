package com.mxm.licitacao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTransacao {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String descricao;
}
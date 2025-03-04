package com.mxm.licitacao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetodoPagamento {
    DINHEIRO,
    PIX,
    BOLETO,
    TRANSFERENCIA,
    CARTAO_CREDITO,
    CARTAO_DEBITO,
    OUTRO
}

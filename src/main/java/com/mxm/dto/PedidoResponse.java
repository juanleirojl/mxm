package com.mxm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mxm.enums.StatusPagamento;
import com.mxm.enums.StatusPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PedidoResponse {

    private Long id;
    private String numero;
    private LocalDate data;
    private Long clienteId;
    private Long cimentoId;
    private Integer quantidade;
    private BigDecimal precoCimentoComprado;
    private BigDecimal precoCimentoVendido;
    private BigDecimal frete;
    private BigDecimal valorReceberCliente;
    private BigDecimal valorNotaFabrica;
    private BigDecimal imposto;
    private BigDecimal lucroFinal;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private String mensagem;
}

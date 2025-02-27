package com.mxm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private String placa;
    private String motorista;
    private LocalDate data;
    private ClienteResponse cliente;
    private CimentoResponse cimento;
    private Integer quantidade;
    private BigDecimal precoCimentoComprado;
    private BigDecimal precoCimentoVendido;
    private BigDecimal frete;
    private BigDecimal valorReceberCliente;
    private BigDecimal valorNotaFabrica;
    private BigDecimal valorParcial;
    private BigDecimal imposto;
    private BigDecimal lucroFinal;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;
    private String mensagem;
    
    private BigDecimal impostoPorSaco;
    private BigDecimal fretePorSaco;
    private BigDecimal custoSaco;
    private BigDecimal lucroPorSaco;
    private LocalDateTime dataCadastro;
    
    private List<PagamentoParcialResponse> pagamentosParciais;
    private String jsonPagamentosParciais;
    
    @Data
    @Builder
    @AllArgsConstructor
    public static class ClienteResponse{
      private Long id;
      private String nome;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    public static class CimentoResponse{
      private Long id;
      private String marca;
    }

}

package com.mxm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.mxm.enums.StatusOrcamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orcamento_cimento")
public class OrcamentoCimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "empresa_id", nullable = false)
  private Empresa empresa; // Empresa responsável pelo orçamento

  @ManyToOne
  @JoinColumn(name = "cimento_id", nullable = false)
  private Cimento cimento; // Tipo/marca do cimento no orçamento

  @ManyToOne
  @JoinColumn(name = "cidade_id", nullable = false)
  private Cidade cidade; // Cidade onde o orçamento será aplicado

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = true) // Cliente opcional
  private Cliente cliente;

  private Integer quantidadeSacos; // Quantidade de sacos no orçamento

  private BigDecimal custoSacoFabrica; // Valor unitário que compramos na fabrica
  private BigDecimal custoPedidoFabrica; // Valor Pedido fabrica quantidadeSacos*valorSacoFabrica
  private BigDecimal custoImposto; // Valor do imposto (5% do valor total dos sacos)
  private BigDecimal custoFrete; // Valor do frete
  
  private BigDecimal valorSacoCliente; // Valor unitário que o cliente pagará
  private BigDecimal valorPedidoCliente; // Valor Pedido Cliente quantidadeSacos*valorSacoCliente

  private BigDecimal custoTotal; // Valor total (custoPedidoFabrica + frete + imposto)
  private BigDecimal valorLucro; // valorPedidoCliente - custoTotal

  private LocalDateTime dataOrcamento; // Data e hora do orçamento

  @Enumerated(EnumType.STRING)
  private StatusOrcamento status; // Status do orçamento (PENDENTE, APROVADO, CANCELADO)
}

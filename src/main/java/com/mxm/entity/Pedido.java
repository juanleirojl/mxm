package com.mxm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mxm.enums.StatusPagamento;
import com.mxm.enums.StatusPedido;

import jakarta.persistence.Column;
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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String numero;

  @Column(name = "data", columnDefinition = "TEXT")
  private LocalDate data;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "cimento_id")
  private Cimento cimento;

  private String placaCarro;
  private Integer quantidade;//5
  private BigDecimal precoCimentoVendido;//2
  private BigDecimal valorReceberCliente;
  private BigDecimal precoCimentoComprado;//1
  private BigDecimal valorNotaFabrica;
  
  private BigDecimal frete;//3
  private BigDecimal imposto;

  private BigDecimal lucroFinal;

  @Enumerated(EnumType.STRING)
  private StatusPedido statusPedido;
  
  @Enumerated(EnumType.STRING)
  private StatusPagamento statusPagamento;
  
  private BigDecimal valorParcial;//4 ainda n calculado
  private BigDecimal impostoPorSaco;
  private BigDecimal fretePorSaco;
  private BigDecimal custoSaco;
  private BigDecimal lucroPorSaco;
  
}

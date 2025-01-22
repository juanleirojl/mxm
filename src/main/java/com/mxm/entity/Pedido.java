package com.mxm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.mxm.enums.StatusPedido;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String numero;

  private LocalDate data;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "cimento_id")
  private Cimento cimento;

  @ManyToOne
  @JoinColumn(name = "fabrica_id")

  private Fabrica fabrica;
  private Integer quantidade;
  private BigDecimal precoCompra;
  private BigDecimal valorCompra;
  private BigDecimal precoVenda;
  private BigDecimal valorVenda;
  private BigDecimal valorImposto;

  @ManyToOne
  @JoinColumn(name = "frete_id")

  private Frete frete;
  private String placaCarro;
  private BigDecimal valorFrete;

  @Enumerated(EnumType.STRING)
  private StatusPedido status;

  @ManyToOne
  @JoinColumn(name = "orcamento_cimento_id", nullable = true)
  private PrecoCimento orcamentoCimento;
}

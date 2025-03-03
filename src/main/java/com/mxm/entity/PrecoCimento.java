package com.mxm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import groovy.transform.EqualsAndHashCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "preco_cimento", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"empresa_id", "cimento_id", "cidade_id", "quantidade_sacos"})})
@EqualsAndHashCode(callSuper=false)
public class PrecoCimento extends EntidadeBase{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "empresa_id", nullable = false)
  private Empresa empresa;

  @ManyToOne
  @JoinColumn(name = "cimento_id", nullable = false)
  private Cimento cimento;

  @ManyToOne
  @JoinColumn(name = "cidade_id", nullable = false)
  private Cidade cidade;

  private Integer quantidadeSacos;

  private BigDecimal valor;

  private LocalDateTime data;
}



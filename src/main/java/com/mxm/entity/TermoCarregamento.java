package com.mxm.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "termo_carregamento")
@Builder
public class TermoCarregamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String motorista;
  private String placa;
  private Long quantidadeSacos;
  private Long numeroPedido;
  private LocalDateTime dataCadastro;
  @Column(name = "arquivo")
  private byte[] arquivo;
}

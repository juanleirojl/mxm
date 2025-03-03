package com.mxm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "frete")
public class Frete {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String capacidade;
  private String telefone;
  private String placa;
  private String endereco;

  @ManyToOne
  @JoinColumn(name = "cidade_id")
  private Cidade cidade;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "frete_id")
  private List<Anotacao> anotacoes;

  @ManyToMany
  @JoinTable(name = "frete_fabrica", joinColumns = @JoinColumn(name = "frete_id"),
      inverseJoinColumns = @JoinColumn(name = "fabrica_id"))
  private List<Fabrica> fabricas;

}

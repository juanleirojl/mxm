package com.mxm.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fabrica")
public class Fabrica {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private String responsavel;
  private String telefone;
  private String endereco;

  @ManyToOne
  @JoinColumn(name = "cidade_id")
  private Cidade cidade;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "fabrica_id")
  private List<Anotacao> anotacoes;

  @OneToMany(mappedBy = "fabrica")
  @ToString.Exclude
  private List<Cimento> cimentos;


}

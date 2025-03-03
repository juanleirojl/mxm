package com.mxm.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import groovy.transform.ToString;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(excludes = "pedidos") 
@Table(name = "cliente")
public class Cliente extends EntidadeBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String telefone;
    private String responsavel;
    private String endereco;
    private String email;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    private List<Anotacao> anotacoes;

    @Builder.Default
    @OneToMany(mappedBy = "cliente")
    @JsonBackReference
    private List<Pedido> pedidos = new ArrayList<>();
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", cidade=" + (cidade != null ? cidade.getNome() : "N/A") +
                ", totalPedidos=" + (pedidos != null ? pedidos.size() : 0) +
                ", criadoPor=" + (getCriadoPor() != null ? getCriadoPor().getNome() : "N/A") +
                ", criadoEm=" + getCriadoEm() +
                '}';
    }

}


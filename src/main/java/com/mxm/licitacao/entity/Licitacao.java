package com.mxm.licitacao.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.mxm.entity.EntidadeBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "licitacoes")
public class Licitacao extends  EntidadeBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(name = "dataInicio", columnDefinition = "TEXT",nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dataFim", columnDefinition = "TEXT",nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldoAtual;
    
    @OneToMany(mappedBy = "licitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;
}
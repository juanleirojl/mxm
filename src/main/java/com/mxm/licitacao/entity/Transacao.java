package com.mxm.licitacao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mxm.entity.EntidadeBase;
import com.mxm.licitacao.enums.MetodoPagamento;
import com.mxm.licitacao.enums.TipoTransacao;
import com.mxm.models.Usuario;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transacoes")
public class Transacao extends EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPagamento metodoPagamento;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario responsavel; // Quem registrou essa transação
    
    @ManyToOne
    @JoinColumn(name = "licitacao_id", nullable = false)
    private Licitacao licitacao;
    
    @Column(nullable = false)
    private String origemDestino; // De onde veio o dinheiro ou para onde foi

    // Métodos para verificar se é entrada ou saída
    public boolean isEntrada() {
        return categoria.getTipoTransacao() == TipoTransacao.ENTRADA;
    }

    public boolean isSaida() {
        return categoria.getTipoTransacao() == TipoTransacao.SAIDA;
    }
}

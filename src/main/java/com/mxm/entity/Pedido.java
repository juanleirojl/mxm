package com.mxm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mxm.enums.StatusPagamento;
import com.mxm.enums.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String placa;
    private String motorista;

    @Column(name = "data", columnDefinition = "TEXT")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonManagedReference
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cimento_id")
    private Cimento cimento;

    private Integer quantidade;
    private BigDecimal precoCimentoVendido;
    private BigDecimal valorReceberCliente;
    private BigDecimal precoCimentoComprado;
    private BigDecimal valorNotaFabrica;
    private BigDecimal frete;
    private BigDecimal imposto;
    private BigDecimal lucroFinal;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    private BigDecimal valorParcial;
    private BigDecimal impostoPorSaco;
    private BigDecimal fretePorSaco;
    private BigDecimal custoSaco;
    private BigDecimal lucroPorSaco;
    private LocalDateTime dataCadastro;

    @Builder.Default
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PagamentoParcial> pagamentosParciais = new ArrayList<>();
    
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", data=" + data +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") + // Evita carregar o objeto inteiro
                ", cimento=" + (cimento != null ? cimento.getMarca() : "null") +
                ", quantidade=" + quantidade +
                ", precoCimentoVendido=" + precoCimentoVendido +
                ", valorReceberCliente=" + valorReceberCliente +
                ", statusPedido=" + statusPedido +
                ", statusPagamento=" + statusPagamento +
                ", placa='" + placa + '\'' +
                ", motorista='" + motorista + '\'' +
                ", pagamentosParciais=" + pagamentosParciais.size() + " pagamentos" + // Mostra o total de pagamentos
                '}';
    }
}


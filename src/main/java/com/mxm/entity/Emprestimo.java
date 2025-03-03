package com.mxm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "emprestimos")
public class Emprestimo extends EntidadeBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorEmprestado;
    private BigDecimal valorPago;
    private LocalDate dataEmprestimo;
    private String observacao;

    public BigDecimal getSaldoDevedor() {
        return valorEmprestado.subtract(valorPago != null ? valorPago : BigDecimal.ZERO);
    }
    
    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", valorEmprestado=" + valorEmprestado +
                ", valorPago=" + valorPago +
                ", saldoDevedor=" + getSaldoDevedor() +
                ", dataEmprestimo=" + dataEmprestimo +
                ", observacao='" + observacao + '\'' +
                '}';
    }

}

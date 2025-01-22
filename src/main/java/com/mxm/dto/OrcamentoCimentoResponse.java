package com.mxm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.mxm.entity.OrcamentoCimento;
import com.mxm.entity.PrecoCimento;
import com.mxm.enums.StatusOrcamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoCimentoResponse {

    private Long id;
    private String empresa;
    private String cimento;
    private String cidade;
    private String cliente;
    private Integer quantidadeSacos;
    private BigDecimal custoSacoFabrica;
    private BigDecimal custoPedidoFabrica;
    private BigDecimal custoImposto;
    private BigDecimal valorSacoCliente;
    private BigDecimal valorPedidoCliente;
    private BigDecimal custoFrete;
    private BigDecimal custoTotal;
    private BigDecimal valorLucro;
    private LocalDateTime dataOrcamento;
    private StatusOrcamento status;

    public static OrcamentoCimentoResponse fromEntity(OrcamentoCimento orcamento, PrecoCimento precoCimento) {
        return OrcamentoCimentoResponse.builder()
            .id(orcamento.getId())
            .empresa(orcamento.getEmpresa().getNome())
            .cimento(orcamento.getCimento().getMarca())
            .cidade(orcamento.getCidade().getNome())
            .cliente(orcamento.getCliente() != null ? orcamento.getCliente().getNome() : "Cliente desconhecido")
            .quantidadeSacos(orcamento.getQuantidadeSacos())
            .custoSacoFabrica(orcamento.getCustoSacoFabrica())
            .custoPedidoFabrica(orcamento.getCustoPedidoFabrica())
            .custoImposto(orcamento.getCustoImposto())
            .valorSacoCliente(orcamento.getValorSacoCliente())
            .valorPedidoCliente(orcamento.getValorPedidoCliente())
            .custoFrete(orcamento.getCustoFrete())
            .custoTotal(orcamento.getCustoTotal())
            .valorLucro(orcamento.getValorLucro())
            .dataOrcamento(orcamento.getDataOrcamento())
            .status(orcamento.getStatus())
            .build();
    }
}

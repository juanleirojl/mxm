package com.mxm.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrcamentoCimentoRequest {

    @NotNull
    private Long empresaId;
    @NotNull
    private Long cimentoId;
    @NotNull
    private Long cidadeId;
    private Long clienteId;
    
    @NotNull
    @Positive
    private Integer quantidadeSacos;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal valorUnitario;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal valorFrete;
    
//    public static OrcamentoCimento toEntity(OrcamentoCimentoRequest request, EmpresaRepository empresaRepository,
//        CimentoRepository cimentoRepository, CidadeRepository cidadeRepository, ClienteRepository clienteRepository) {
//      OrcamentoCimento orcamento = new OrcamentoCimento();
//      
//      orcamento.setEmpresa(empresaRepository.findById(request.getEmpresaId()).orElseThrow(() -> new RuntimeException("Preço do cimento não encontrado para os critérios informados.")));
//      orcamento.setCimento(cimentoRepository.findById(request.getCimentoId()).orElseThrow(() -> new RuntimeException("Cimento não encontrado.")));
//      orcamento.setCidade(cidadeRepository.findById(request.getCidadeId()).orElseThrow(() -> new RuntimeException("Cidade não encontrada.")));
//      orcamento.setCliente(
//              request.getClienteId() != null ? clienteRepository.findById(request.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado.")) : null
//      );
//      orcamento.setQuantidadeSacos(request.getQuantidadeSacos());
//      orcamento.setValorSacoCliente(request.getValorUnitario());
//      orcamento.setCustoFrete(request.getValorFrete());
//
//      return orcamento;
//  }
}

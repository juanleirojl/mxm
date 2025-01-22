package com.mxm.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mxm.dto.OrcamentoCimentoByNameRequest;
import com.mxm.dto.OrcamentoCimentoRequest;
import com.mxm.dto.OrcamentoCimentoResponse;
import com.mxm.entity.Cidade;
import com.mxm.entity.Cimento;
import com.mxm.entity.Cliente;
import com.mxm.entity.Empresa;
import com.mxm.entity.OrcamentoCimento;
import com.mxm.entity.PrecoCimento;
import com.mxm.enums.StatusOrcamento;
import com.mxm.repository.CidadeRepository;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.repository.EmpresaRepository;
import com.mxm.repository.OrcamentoCimentoRepository;
import com.mxm.repository.PrecoCimentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrcamentoCimentoService {

    private final OrcamentoCimentoRepository orcamentoCimentoRepository;
    private final PrecoCimentoRepository precoCimentoRepository;
    private final EmpresaRepository empresaRepository;
    private final CimentoRepository cimentoRepository;
    private final CidadeRepository cidadeRepository;
    private final ClienteRepository clienteRepository;

    public OrcamentoCimentoResponse criarOrcamento(OrcamentoCimentoRequest request) {
        PrecoCimento precoCimento = recuperaPrecoCimento(request);
        OrcamentoCimento orcamento = criarObjetoOrcamento(request, precoCimento);

        OrcamentoCimento savedOrcamento = orcamentoCimentoRepository.save(orcamento);
        return OrcamentoCimentoResponse.fromEntity(savedOrcamento, precoCimento);
    }

    public OrcamentoCimentoResponse criarOrcamentoPorNomes(OrcamentoCimentoByNameRequest request) {
        // Recupera as entidades com base nos nomes fornecidos
        Empresa empresa = empresaRepository.findByNomeIgnoreCase(request.getEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada: " + request.getEmpresa()));

        Cimento cimento = cimentoRepository.findByMarcaIgnoreCase(request.getCimento())
                .orElseThrow(() -> new RuntimeException("Cimento não encontrado: " + request.getCimento()));

        Cidade cidade = cidadeRepository.findByNomeIgnoreCase(request.getCidade())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada: " + request.getCidade()));

        Cliente cliente = Optional.ofNullable(request.getCliente())
                .flatMap(clienteRepository::findByNomeIgnoreCase)
                .orElse(null);

        // Cria o objeto de requisição com os IDs das entidades
        OrcamentoCimentoRequest orcamentoRequest = OrcamentoCimentoRequest.builder()
                .empresaId(empresa.getId())
                .cimentoId(cimento.getId())
                .cidadeId(cidade.getId())
                .clienteId(cliente != null ? cliente.getId() : null)
                .quantidadeSacos(request.getQuantidadeSacos())
                .valorUnitario(request.getValorUnitario())
                .valorFrete(request.getValorFrete())
                .build();

        return criarOrcamento(orcamentoRequest);
    }
    


    private PrecoCimento recuperaPrecoCimento(OrcamentoCimentoRequest request) {
      
      Empresa empresa = empresaRepository.findById(request.getEmpresaId())
          .orElseThrow(() -> new RuntimeException("Empresa não encontrada: " + request.getEmpresaId()));

      Cimento cimento = cimentoRepository.findById(request.getCimentoId())
              .orElseThrow(() -> new RuntimeException("Cimento não encontrado: " + request.getCimentoId()));
    
      Cidade cidade = cidadeRepository.findById(request.getCidadeId())
              .orElseThrow(() -> new RuntimeException("Cidade não encontrada: " + request.getCidadeId()));
    
//      Cliente cliente = Optional.ofNullable(request.getClienteId())
//              .flatMap(clienteRepository::findById)
//              .orElse(null);
  
  
        return recuperaPrecoCimento(request.getQuantidadeSacos(), empresa, cimento, cidade);
    }
    
    private PrecoCimento recuperaPrecoCimento(Integer quantidadeSacos, Empresa empresa,
        Cimento cimento, Cidade cidade) {
      // Tenta buscar valores menores ou iguais
      List<PrecoCimento> menoresOuIguais = precoCimentoRepository.buscaMenorOuIgual(empresa.getId(),
          cimento.getId(), cidade.getId(), quantidadeSacos);

      // Retorna o primeiro menor ou igual, se existir
      if (!menoresOuIguais.isEmpty()) {
        return menoresOuIguais.get(0); // O mais próximo será o primeiro
      }

      // Se não encontrar, busca valores maiores
      List<PrecoCimento> maiores = precoCimentoRepository.buscaMaior(empresa.getId(),
          cimento.getId(), cidade.getId(), quantidadeSacos);

      // Retorna o primeiro maior, se existir
      if (!maiores.isEmpty()) {
        return maiores.get(0);
      }

      // Se não encontrar nenhum preço, lança exceção
      throw new RuntimeException(String.format(
          "Preço do cimento não encontrado para Empresa= %s (%d), Cimento= %s (%d), Cidade= %s (%d), Quantidade= %d",
          empresa.getNome(), empresa.getId(), cimento.getMarca(), cimento.getId(), cidade.getNome(),
          cidade.getId(), quantidadeSacos));
    }


    private PrecoCimento recuperaPrecoCimento2(Integer quantidadeSacos, Empresa empresa,
        Cimento cimento, Cidade cidade) {
      
   // Tenta encontrar o intervalo mais próximo maior ou igual à quantidadeSacos
      Optional<PrecoCimento> precoMaisProximo = precoCimentoRepository.buscaPrecoCimentoMaisProximo(
          empresa.getId(), cimento.getId(), cidade.getId(), quantidadeSacos);

      // Se não houver intervalo maior, retorna o menor disponível
      return precoMaisProximo.orElseGet(() -> {
        return precoCimentoRepository
            .findAllByEmpresaAndCimentoAndCidade(empresa.getId(), cimento.getId(), cidade.getId())
            .stream().min(Comparator.comparingInt(PrecoCimento::getQuantidadeSacos))
            .orElseThrow(() -> new RuntimeException(
                String.format(
                    "Preço do cimento não encontrado para Empresa= %s (%d), Cimento= %s (%d), Cidade= %s (%d), Quantidade= %d",
                    empresa.getNome(),empresa.getId(), cimento.getMarca(),cimento.getId(),  cidade.getNome(), cidade.getId(), quantidadeSacos
            )));
      });
      
//      return precoCimentoRepository.buscaPrecoCimento(
//          empresa.getId(),
//          cimento.getId(),
//          cidade.getId(),
//          quantidadeSacos
//      ).orElseThrow(() -> new RuntimeException(
//              String.format(
//                      "Preço do cimento não encontrado para Empresa= %s (%d), Cimento= %s (%d), Cidade= %s (%d), Quantidade= %d",
//                      empresa.getNome(),empresa.getId(), cimento.getMarca(),cimento.getId(),  cidade.getNome(), cidade.getId(), quantidadeSacos
//              )
//      ));
    }


    public List<OrcamentoCimentoResponse> listarOrcamentos() {
        return orcamentoCimentoRepository.findAll().stream()
                .map(orcamento -> {
                    PrecoCimento precoCimento = recuperaPrecoCimento(orcamento.getQuantidadeSacos(),
                        orcamento.getEmpresa(), orcamento.getCimento(), orcamento.getCidade());
                    return OrcamentoCimentoResponse.fromEntity(orcamento, precoCimento);
                })
                .toList();
    }
    
//    private PrecoCimento recuperaPrecoCimentoFromEntity(OrcamentoCimento orcamento) {
//    return precoCimentoRepository.findByCriteria(
//            orcamento.getEmpresa().getId(),
//            orcamento.getCimento().getId(),
//            orcamento.getCidade().getId(),
//            orcamento.getQuantidadeSacos()
//    ).orElseThrow(() -> new RuntimeException("Preço do cimento não encontrado para o orçamento ID=" + orcamento.getId()));
//  }

    private OrcamentoCimentoRequest createOrcamentoRequest(OrcamentoCimento orcamento) {
      return OrcamentoCimentoRequest.builder()
          .empresaId(orcamento.getEmpresa().getId())
          .cimentoId(orcamento.getCimento().getId())
          .cidadeId(orcamento.getCidade().getId())
          .clienteId(orcamento.getCliente() != null ? orcamento.getCliente().getId() : null)
          .quantidadeSacos(orcamento.getQuantidadeSacos())
          .valorUnitario(orcamento.getValorSacoCliente())
          .valorFrete(orcamento.getCustoFrete())
          .build();
    }

    private OrcamentoCimento criarObjetoOrcamento(OrcamentoCimentoRequest request, PrecoCimento precoCimento) {
      BigDecimal valorCliente = request.getValorUnitario().multiply(new BigDecimal(request.getQuantidadeSacos()));
      BigDecimal valorImposto = precoCimento.getValor().multiply(new BigDecimal(request.getQuantidadeSacos())).multiply(new BigDecimal("0.05"));
      BigDecimal valorFrete = request.getValorFrete() != null ? request.getValorFrete() : BigDecimal.ZERO;

      BigDecimal valorLucro = valorCliente
              .subtract(precoCimento.getValor().multiply(new BigDecimal(request.getQuantidadeSacos())))
              .subtract(valorFrete)
              .subtract(valorImposto);

      BigDecimal custoTotal = precoCimento.getValor().multiply(new BigDecimal(request.getQuantidadeSacos()))
              .add(valorFrete)
              .add(valorImposto);

      return OrcamentoCimento.builder()
              .empresa(empresaRepository.findById(request.getEmpresaId()).orElseThrow())
              .cimento(cimentoRepository.findById(request.getCimentoId()).orElseThrow())
              .cidade(cidadeRepository.findById(request.getCidadeId()).orElseThrow())
              .cliente(request.getClienteId() != null ? clienteRepository.findById(request.getClienteId()).orElse(null) : null)
              .quantidadeSacos(request.getQuantidadeSacos())
              .custoSacoFabrica(precoCimento.getValor())
              .custoPedidoFabrica(precoCimento.getValor().multiply(new BigDecimal(request.getQuantidadeSacos())))
              .custoImposto(valorImposto)
              .valorSacoCliente(request.getValorUnitario())
              .valorPedidoCliente(valorCliente)
              .custoTotal(custoTotal)
              .valorLucro(valorLucro)
              .custoFrete(valorFrete)
              .dataOrcamento(LocalDateTime.now())
              .status(StatusOrcamento.PENDENTE)
              .build();
  }
}

package com.mxm.services;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxm.entity.OrcamentoCimento;
import com.mxm.entity.Pedido;
import com.mxm.enums.StatusOrcamento;
import com.mxm.enums.StatusPedido;
import com.mxm.repository.OrcamentoCimentoRepository;
import com.mxm.repository.PedidoRepository;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private OrcamentoCimentoRepository orcamentoCimentoRepository;

  public Pedido criarPedido(Pedido pedido) {
    // Aqui você pode adicionar lógica de validação ou cálculo antes de salvar o pedido
    return pedidoRepository.save(pedido);
  }

  public Pedido criarPedidoDeOrcamento(Long orcamentoId) {
    OrcamentoCimento orcamento = orcamentoCimentoRepository.findById(orcamentoId)
        .orElseThrow(() -> new RuntimeException("Orçamento não encontrado!"));

    if (orcamento.getStatus() != StatusOrcamento.PENDENTE) {
      throw new RuntimeException("Somente orçamentos pendentes podem gerar pedidos!");
    }

    Pedido pedido = new Pedido();

    // Define o cliente se existir no orçamento
    if (orcamento.getCliente() != null) {
      pedido.setCliente(orcamento.getCliente());
    }

    pedido.setCimento(orcamento.getCimento());
    pedido.setQuantidade(orcamento.getQuantidadeSacos());
    pedido.setPrecoCompra(orcamento.getValorSacoCliente());
    pedido.setValorCompra(orcamento.getValorPedidoCliente());
    pedido.setData(LocalDate.now());
    pedido.setStatus(StatusPedido.PROCESSANDO);

    // Atualiza o status do orçamento para APROVADO
    orcamento.setStatus(StatusOrcamento.APROVADO);
    orcamentoCimentoRepository.save(orcamento);

    // Salva o pedido
    return pedidoRepository.save(pedido);
  }


}

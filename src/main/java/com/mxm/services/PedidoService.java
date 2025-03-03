package com.mxm.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mxm.dto.PedidoRequest;
import com.mxm.dto.PedidoRequest.PagamentoParcialRequest;
import com.mxm.entity.PagamentoParcial;
import com.mxm.entity.Pedido;
import com.mxm.enums.StatusPagamento;
import com.mxm.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

	private static final double IMPOSTO = 0.06;
	private final PedidoRepository pedidoRepository;

    public Pedido criarPedido(Pedido pedido) {
    	if (pedido.getPagamentosParciais() == null) {
            pedido.setPagamentosParciais(new ArrayList<>());
        }
        preencherCampos(pedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
    	return pedidoRepository.buscarPedidosPorFiltros(null, null, null, null);
    }
    
    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }
    
    public List<Pedido> buscaPorCliente(Long clienteId){
    	return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional
    public void atualizarPedido(Long id, PedidoRequest pedidoRequest) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        // Remove TODOS os pagamentos existentes antes de adicionar os novos
        if (pedidoExistente.getPagamentosParciais() != null) {
            pedidoExistente.getPagamentosParciais().clear();
        } else {
            pedidoExistente.setPagamentosParciais(new ArrayList<>());
        }

        // Adiciona os novos pagamentos parciais
        if (pedidoRequest.getPagamentosParciais() != null) {
            for (PagamentoParcialRequest novoPagamento : pedidoRequest.getPagamentosParciais()) {
                
                // **Se valor ou data forem nulos, ignora**
                if (novoPagamento.getValor() == null || novoPagamento.getData() == null) {
                    continue;
                }

                // **Adiciona um novo pagamento**
                pedidoExistente.getPagamentosParciais().add(
                    PagamentoParcial.builder()
                        .valor(novoPagamento.getValor())
                        .data(novoPagamento.getData())
                        .pedido(pedidoExistente)
                        .build()
                );
            }
        }

        // **Atualiza os outros campos do pedido**
        pedidoExistente.setStatusPedido(pedidoRequest.getStatusPedido());
        pedidoExistente.setStatusPagamento(pedidoRequest.getStatusPagamento());
        pedidoExistente.setData(pedidoRequest.getData());
        pedidoExistente.setQuantidade(pedidoRequest.getQuantidade());
        pedidoExistente.setPrecoCimentoComprado(pedidoRequest.getPrecoCimentoComprado());
        pedidoExistente.setPrecoCimentoVendido(pedidoRequest.getPrecoCimentoVendido());
        pedidoExistente.setFrete(pedidoRequest.getFrete());
        pedidoExistente.setValorParcial(pedidoRequest.getValorParcial());
        pedidoExistente.setNumero(pedidoRequest.getNumero());
        pedidoExistente.setPlaca(pedidoRequest.getPlaca());
        pedidoExistente.setMotorista(pedidoRequest.getMotorista());
        preencherCampos(pedidoExistente);

        pedidoRepository.save(pedidoExistente);
    }

    public void deletarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }

    public void preencherCampos(Pedido pedido) {
        BigDecimal quantidade = BigDecimal.valueOf(pedido.getQuantidade());

        pedido.setDataCadastro(LocalDateTime.now());
        pedido.setValorNotaFabrica(formatarDuasCasas(pedido.getPrecoCimentoComprado().multiply(quantidade)));
        pedido.setValorReceberCliente(formatarDuasCasas(pedido.getPrecoCimentoVendido().multiply(quantidade)));
        pedido.setImposto(formatarDuasCasas(pedido.getValorNotaFabrica().multiply(BigDecimal.valueOf(IMPOSTO))));

        BigDecimal impostoPorSaco = formatarDuasCasas(pedido.getImposto().divide(quantidade, 3, RoundingMode.HALF_UP));
        BigDecimal fretePorSaco = formatarDuasCasas(pedido.getFrete().divide(quantidade, 3, RoundingMode.HALF_UP));
        BigDecimal custoSaco = formatarDuasCasas(pedido.getPrecoCimentoComprado().add(fretePorSaco).add(impostoPorSaco));
        BigDecimal lucroPorSaco = formatarDuasCasas(pedido.getPrecoCimentoVendido().subtract(custoSaco));
//        BigDecimal lucroFinal = formatarDuasCasas(lucroPorSaco.multiply(quantidade));
        BigDecimal lucroFinalCompleto = formatarDuasCasas(quantidade.multiply(pedido.getPrecoCimentoVendido())
                .subtract(quantidade.multiply(pedido.getPrecoCimentoComprado()).add(pedido.getFrete()).add(pedido.getImposto())));

        pedido.setImpostoPorSaco(impostoPorSaco);
        pedido.setFretePorSaco(fretePorSaco);
        pedido.setCustoSaco(custoSaco);
        pedido.setLucroPorSaco(lucroPorSaco);
        pedido.setLucroFinal(lucroFinalCompleto);

        if (pedido.getValorParcial() != null && pedido.getValorParcial().compareTo(pedido.getValorReceberCliente()) >= 0) {
            pedido.setStatusPagamento(StatusPagamento.PAGO);
        } else if (pedido.getValorParcial() != null && pedido.getValorParcial().compareTo(BigDecimal.ZERO) > 0 
                   && pedido.getValorParcial().compareTo(pedido.getValorReceberCliente()) < 0) {
            pedido.setStatusPagamento(StatusPagamento.PARCIAL);
        } else {
            pedido.setStatusPagamento(StatusPagamento.PENDENTE);
        }

    }


    
    private BigDecimal formatarDuasCasas(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

}

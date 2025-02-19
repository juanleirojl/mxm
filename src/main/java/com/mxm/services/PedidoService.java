package com.mxm.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mxm.entity.Pedido;
import com.mxm.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

	private static final double IMPOSTO = 0.06;
	private final PedidoRepository pedidoRepository;

    public Pedido criarPedido(Pedido pedido) {
        preencherCampos(pedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        pedidoExistente.setQuantidade(pedidoAtualizado.getQuantidade());
        pedidoExistente.setPrecoCimentoComprado(pedidoAtualizado.getPrecoCimentoComprado());
        pedidoExistente.setPrecoCimentoVendido(pedidoAtualizado.getPrecoCimentoVendido());
        pedidoExistente.setFrete(pedidoAtualizado.getFrete());
        preencherCampos(pedidoExistente);

        return pedidoRepository.save(pedidoExistente);
    }

    public void deletarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }

    public void preencherCampos(Pedido pedido) {
        BigDecimal quantidade = BigDecimal.valueOf(pedido.getQuantidade());

        pedido.setData(LocalDate.now());
        pedido.setValorNotaFabrica(formatarDuasCasas(pedido.getPrecoCimentoComprado().multiply(quantidade)));
        pedido.setValorReceberCliente(formatarDuasCasas(pedido.getPrecoCimentoVendido().multiply(quantidade)));
        pedido.setImposto(formatarDuasCasas(pedido.getValorNotaFabrica().multiply(BigDecimal.valueOf(IMPOSTO))));

        BigDecimal impostoPorSaco = formatarDuasCasas(pedido.getImposto().divide(quantidade, 3, RoundingMode.HALF_UP));
        BigDecimal fretePorSaco = formatarDuasCasas(pedido.getFrete().divide(quantidade, 3, RoundingMode.HALF_UP));
        BigDecimal custoSaco = formatarDuasCasas(pedido.getPrecoCimentoComprado().add(fretePorSaco).add(impostoPorSaco));
        BigDecimal lucroPorSaco = formatarDuasCasas(pedido.getPrecoCimentoVendido().subtract(custoSaco));
        BigDecimal lucroFinal = formatarDuasCasas(lucroPorSaco.multiply(quantidade));
        BigDecimal lucroFinalCompleto = formatarDuasCasas(quantidade.multiply(pedido.getPrecoCimentoVendido())
        		.subtract(quantidade.multiply(pedido.getPrecoCimentoComprado()).add(pedido.getFrete()).add(pedido.getImposto())));
        
        pedido.setImpostoPorSaco(impostoPorSaco);
        pedido.setFretePorSaco(fretePorSaco);
        pedido.setCustoSaco(custoSaco);
        pedido.setLucroPorSaco(lucroPorSaco);
        pedido.setLucroFinal(lucroFinalCompleto);
    }

    
    private BigDecimal formatarDuasCasas(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

}

package com.mxm.helpers;

import org.springframework.stereotype.Component;

import com.mxm.dto.PedidoRequest;
import com.mxm.dto.PedidoResponse;
import com.mxm.entity.Cimento;
import com.mxm.entity.Cliente;
import com.mxm.entity.Pedido;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final CimentoRepository cimentoRepository;
    private final ClienteRepository clienteRepository;

    public Pedido toEntity(PedidoRequest request) {
        Cimento cimento = cimentoRepository.findById(request.getCimentoId())
                .orElseThrow(() -> new RuntimeException("Cimento não encontrado: " + request.getCimentoId()));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + request.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCimento(cimento);
        pedido.setCliente(cliente);
        pedido.setQuantidade(request.getQuantidade());
        pedido.setPrecoCimentoComprado(request.getPrecoCimentoComprado());
        pedido.setPrecoCimentoVendido(request.getPrecoCimentoVendido());
        pedido.setFrete(request.getFrete());
        pedido.setValorParcial(request.getValorParcial());
        pedido.setStatusPagamento(request.getStatusPagamento());
        pedido.setStatusPedido(request.getStatusPedido());
        return pedido;
    }

    public PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .data(pedido.getData())
                .clienteId(pedido.getCliente().getId())
                .cimentoId(pedido.getCimento().getId())
                .quantidade(pedido.getQuantidade())
                .precoCimentoComprado(pedido.getPrecoCimentoComprado())
                .precoCimentoVendido(pedido.getPrecoCimentoVendido())
                .frete(pedido.getFrete())
                .valorReceberCliente(pedido.getValorReceberCliente())
                .valorNotaFabrica(pedido.getValorNotaFabrica())
                .imposto(pedido.getImposto())
                .lucroFinal(pedido.getLucroFinal())
                .statusPedido(pedido.getStatusPedido())
                .statusPagamento(pedido.getStatusPagamento())
                .mensagem("Pedido cadastrado com sucesso!")
                .build();
    }
}

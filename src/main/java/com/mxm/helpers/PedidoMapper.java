package com.mxm.helpers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mxm.dto.PagamentoParcialResponse;
import com.mxm.dto.PedidoRequest;
import com.mxm.dto.PedidoResponse;
import com.mxm.dto.PedidoResponse.CimentoResponse;
import com.mxm.dto.PedidoResponse.ClienteResponse;
import com.mxm.entity.Cimento;
import com.mxm.entity.Cliente;
import com.mxm.entity.PagamentoParcial;
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
        pedido.setData(request.getData());
        pedido.setCimento(cimento);
        pedido.setCliente(cliente);
        pedido.setQuantidade(request.getQuantidade());
        pedido.setPrecoCimentoComprado(request.getPrecoCimentoComprado());
        pedido.setPrecoCimentoVendido(request.getPrecoCimentoVendido());
        pedido.setFrete(request.getFrete());
        pedido.setValorParcial(request.getValorParcial());
        pedido.setStatusPagamento(request.getStatusPagamento());
        pedido.setStatusPedido(request.getStatusPedido());
        pedido.setPlaca(request.getPlaca());
        pedido.setNumero(request.getNumero());
        pedido.setMotorista(request.getMotorista());
        
        
        var parciais = request.getPagamentosParciais().stream()
       	     .map(p -> PagamentoParcial.builder().valor(p.getValor()).data(p.getData()).pedido(pedido).build())
       	     .toList();
        
        pedido.setPagamentosParciais(parciais);
        return pedido;
    }

    public PedidoResponse toResponse(Pedido pedido) {
      
     var cliente = ClienteResponse.builder()
       .id(pedido.getCliente().getId())
       .nome(pedido.getCliente().getNome())
     .build();
     
     var cimento =CimentoResponse.builder()
     .id(pedido.getCimento().getId())
     .marca(pedido.getCimento().getMarca())
     .build();
     
      var parciais = pedido.getPagamentosParciais().stream()
     .map(p -> PagamentoParcialResponse.builder().id(p.getId()).valor(p.getValor()).data(p.getData()).build())
     .toList();
     
      
        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .placa(pedido.getPlaca())
                .motorista(pedido.getMotorista())
                .data(pedido.getData())
                .cliente(cliente)
                .cimento(cimento)
                .quantidade(pedido.getQuantidade())
                .precoCimentoComprado(pedido.getPrecoCimentoComprado())
                .precoCimentoVendido(pedido.getPrecoCimentoVendido())
                .frete(pedido.getFrete())
                .valorReceberCliente(pedido.getValorReceberCliente())
                .valorNotaFabrica(pedido.getValorNotaFabrica())
                .valorParcial(pedido.getValorParcial())
                .imposto(pedido.getImposto())
                .lucroFinal(pedido.getLucroFinal())
                .statusPedido(pedido.getStatusPedido())
                .statusPagamento(pedido.getStatusPagamento())
                .mensagem("Pedido cadastrado com sucesso!")
                .impostoPorSaco(pedido.getImpostoPorSaco())
                .fretePorSaco(pedido.getFretePorSaco())
                .custoSaco(pedido.getCustoSaco())
                .lucroPorSaco(pedido.getLucroPorSaco())
                .dataCadastro(pedido.getDataCadastro())
                .pagamentosParciais(parciais != null ? parciais : new ArrayList<>())
                .build();
    }
}

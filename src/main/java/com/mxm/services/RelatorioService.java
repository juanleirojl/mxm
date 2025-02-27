package com.mxm.services;

import com.mxm.entity.Pedido;
import com.mxm.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PedidoRepository pedidoRepository;

    public List<Pedido> buscarPedidos(Long clienteId, Long cimentoId, String dataDe, String dataAte) {
        LocalDate dataInicio = (dataDe != null && !dataDe.trim().isEmpty()) ? LocalDate.parse(dataDe) : null;
        LocalDate dataFim = (dataAte != null && !dataAte.trim().isEmpty()) ? LocalDate.parse(dataAte) : null;

        return pedidoRepository.buscarPedidosPorFiltros(clienteId, cimentoId, dataInicio, dataFim);
    }

    public BigDecimal somarValores(List<Pedido> pedidos, Function<Pedido, BigDecimal> getter) {
        return pedidos.stream()
            .map(p -> {
                BigDecimal valor = getter.apply(p);
                return valor != null ? valor : BigDecimal.ZERO; // Garante que não retorne null
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os valores sem erros
    }

}

package com.mxm.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mxm.dto.PagamentosParciaisDTO;
import com.mxm.dto.PedidosPendentesDTO;
import com.mxm.dto.PedidosPendentesTabelaDTO;
import com.mxm.dto.PedidosPorMotoristaDTO;
import com.mxm.dto.PedidosPorPlacaDTO;
import com.mxm.dto.VendasPorCimentoDTO;
import com.mxm.dto.VendasPorClienteDTO;
import com.mxm.dto.VendasPorMesDTO;
import com.mxm.entity.Emprestimo;
import com.mxm.repository.DashboardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    
    public List<PedidosPendentesDTO> obterPedidosPendentes() {
        return dashboardRepository.buscarPedidosPendentes();
    }

    public List<PagamentosParciaisDTO> obterPagamentosParciais() {
        var lista =  dashboardRepository.buscarPagamentosParciais();
        return lista;
    }

    public List<PedidosPorPlacaDTO> obterPedidosPorPlaca() {
        var lista = dashboardRepository.buscarPedidosPorPlaca();
        return lista;
    }

    public List<PedidosPorMotoristaDTO> obterPedidosPorMotorista() {
        var lista =  dashboardRepository.buscarPedidosPorMotorista();
        return lista;
    }

    public List<VendasPorMesDTO> obterVendasPorMes() {
        LocalDate dataInicio = LocalDate.now().minusMonths(12);
        return dashboardRepository.buscarVendasPorMes(dataInicio);
    }


    public List<VendasPorClienteDTO> obterVendasPorCliente() {
        return dashboardRepository.buscarVendasPorCliente();
    }

    public List<VendasPorCimentoDTO> obterVendasPorCimento() {
        return dashboardRepository.buscarVendasPorCimento();
    }
    
    public List<PedidosPendentesTabelaDTO> obterPedidosPendentesTabela() {
        List<Object[]> resultados = dashboardRepository.buscarPedidosPendentesTabela();

        return resultados.stream()
            .map(obj -> new PedidosPendentesTabelaDTO(
                (String) obj[0],                        // Cliente
                new BigDecimal(obj[1].toString()),      // Total a Pagar
                new BigDecimal(obj[2].toString()),      // Valor Pendente
                ((Number) obj[3]).intValue()            // Dias em Atraso
            ))
            .toList();
    }

    public List<Emprestimo> obterEmprestimos() {
        return dashboardRepository.buscarEmprestimos();
    }

}

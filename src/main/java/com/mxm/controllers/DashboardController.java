package com.mxm.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxm.dto.PagamentosParciaisDTO;
import com.mxm.dto.PedidosPendentesDTO;
import com.mxm.dto.PedidosPendentesTabelaDTO;
import com.mxm.dto.PedidosPorMotoristaDTO;
import com.mxm.dto.PedidosPorPlacaDTO;
import com.mxm.dto.VendasPorCimentoDTO;
import com.mxm.dto.VendasPorClienteDTO;
import com.mxm.dto.VendasPorMesDTO;
import com.mxm.entity.Emprestimo;
import com.mxm.services.DashboardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String exibirDashboard() {
        return "dashboard"; // Retorna o template dashboard.html
    }
    
    @GetMapping("/pedidos-pendentes")
    @ResponseBody
    public List<PedidosPendentesDTO> getPedidosPendentes() {
        return dashboardService.obterPedidosPendentes();
    }

    @GetMapping("/pagamentos-parciais")
    @ResponseBody
    public List<PagamentosParciaisDTO> getPagamentosParciais() {
        return dashboardService.obterPagamentosParciais();
    }

    @GetMapping("/pedidos-por-placa")
    @ResponseBody
    public List<PedidosPorPlacaDTO> getPedidosPorPlaca() {
        return dashboardService.obterPedidosPorPlaca();
    }

    @GetMapping("/pedidos-por-motorista")
    @ResponseBody
    public List<PedidosPorMotoristaDTO> getPedidosPorMotorista() {
        return dashboardService.obterPedidosPorMotorista();
    }

    @GetMapping("/vendas-por-mes")
    @ResponseBody
    public List<VendasPorMesDTO> getVendasPorMes() {
        return dashboardService.obterVendasPorMes();
    }

    @GetMapping("/vendas-por-cliente")
    @ResponseBody
    public List<VendasPorClienteDTO> getVendasPorCliente() {
        return dashboardService.obterVendasPorCliente();
    }

    @GetMapping("/vendas-por-cimento")
    @ResponseBody
    public List<VendasPorCimentoDTO> getVendasPorCimento() {
        return dashboardService.obterVendasPorCimento();
    }
    
    @GetMapping("/pedidos-pendentes-tabela")
    @ResponseBody
    public List<PedidosPendentesTabelaDTO> getPedidosPendentesTabela() {
        return dashboardService.obterPedidosPendentesTabela();
    }
    
    @GetMapping("/emprestimos")
    @ResponseBody
    public List<Emprestimo> getEmprestimos() {
        return dashboardService.obterEmprestimos();
    }

}


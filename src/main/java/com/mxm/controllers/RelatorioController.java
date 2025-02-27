package com.mxm.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mxm.entity.Pedido;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.services.RelatorioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;
    private final ClienteRepository clienteRepository;
    private final CimentoRepository cimentoRepository;

    @GetMapping("/relatorios")
    public String gerarRelatorio(@RequestParam(required = false) Long clienteId,
                                 @RequestParam(required = false) Long cimentoId,
                                 @RequestParam(required = false) String dataDe,
                                 @RequestParam(required = false) String dataAte,
                                 Model model) {

        List<Pedido> pedidosFiltrados = relatorioService.buscarPedidos(clienteId, cimentoId, dataDe, dataAte);

        int totalQuantidade = pedidosFiltrados.stream().mapToInt(Pedido::getQuantidade).sum();
        BigDecimal totalFrete = relatorioService.somarValores(pedidosFiltrados, Pedido::getFrete);
        BigDecimal totalValorNotaFabrica = relatorioService.somarValores(pedidosFiltrados, Pedido::getValorNotaFabrica);
        BigDecimal totalLucroFinal = relatorioService.somarValores(pedidosFiltrados, Pedido::getLucroFinal);
        BigDecimal totalValorParcial = relatorioService.somarValores(pedidosFiltrados, Pedido::getValorParcial);
        BigDecimal totalImposto = relatorioService.somarValores(pedidosFiltrados, Pedido::getImposto);

        // 🔹 MÉDIA ponderada para Imposto por Saco, Frete por Saco, Custo por Saco e Lucro por Saco
        BigDecimal totalImpostoPorSaco = totalQuantidade > 0 ? totalImposto.divide(BigDecimal.valueOf(totalQuantidade), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal totalFretePorSaco = totalQuantidade > 0 ? totalFrete.divide(BigDecimal.valueOf(totalQuantidade), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal totalCustoSaco = totalQuantidade > 0 ? totalValorNotaFabrica.divide(BigDecimal.valueOf(totalQuantidade), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal totalLucroPorSaco = totalQuantidade > 0 ? totalLucroFinal.divide(BigDecimal.valueOf(totalQuantidade), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal totalValorRestante = pedidosFiltrados.stream()
        	    .map(p -> {
        	        BigDecimal valorReceber = p.getValorReceberCliente() != null ? p.getValorReceberCliente() : BigDecimal.ZERO;
        	        BigDecimal valorParcial = p.getValorParcial() != null ? p.getValorParcial() : BigDecimal.ZERO;
        	        return valorReceber.subtract(valorParcial); // Agora sempre terá valores válidos
        	    })
        	    .reduce(BigDecimal.ZERO, BigDecimal::add);


        model.addAttribute("pedidosFiltrados", pedidosFiltrados);
        model.addAttribute("clientes", clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
        model.addAttribute("cimentos", cimentoRepository.findAll(Sort.by(Sort.Direction.ASC, "marca")));

        model.addAttribute("clienteSelecionado", clienteId);
        model.addAttribute("cimentoSelecionado", cimentoId);
        model.addAttribute("dataSelecionadaDe", dataDe);
        model.addAttribute("dataSelecionadaAte", dataAte);
        model.addAttribute("totalValorRestante", totalValorRestante);
        model.addAttribute("totalQuantidade", totalQuantidade);
        model.addAttribute("totalFrete", totalFrete);
        model.addAttribute("totalValorNotaFabrica", totalValorNotaFabrica);
        model.addAttribute("totalLucroFinal", totalLucroFinal);
        model.addAttribute("totalValorParcial", totalValorParcial);
        model.addAttribute("totalImposto", totalImposto);
        model.addAttribute("totalImpostoPorSaco", totalImpostoPorSaco);
        model.addAttribute("totalFretePorSaco", totalFretePorSaco);
        model.addAttribute("totalCustoSaco", totalCustoSaco);
        model.addAttribute("totalLucroPorSaco", totalLucroPorSaco);

        return "relatorios/relatorio-geral";
    }

}

package com.mxm.controllers;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mxm.dto.PedidoRequest;
import com.mxm.dto.PedidoResponse;
import com.mxm.entity.Pedido;
import com.mxm.helpers.PedidoMapper;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.services.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final ClienteRepository clienteRepository;
    private final CimentoRepository cimentoRepository;
    
    @GetMapping("/pedido/cadastrar")
    public String showForm(Model model) {
        model.addAttribute("pedidoRequest", new PedidoRequest());
        model.addAttribute("clientes", clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
        model.addAttribute("cimentos", cimentoRepository.findAll(Sort.by(Sort.Direction.ASC, "marca")));
        return "pedidos/cadastrar-pedido";
    }

    @PostMapping("/pedido/cadastrar")
    public String criarPedido(@Valid PedidoRequest pedidoRequest, RedirectAttributes redirectAttributes) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
        pedidoService.criarPedido(pedido);

        redirectAttributes.addFlashAttribute("mensagem", "Pedido cadastrado com sucesso!");

        return "redirect:/pedido/listar";
    }

    @GetMapping(value = {"/pedido/listar"})
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        List<PedidoResponse> pedidosResponse = pedidos.stream()
                .map(pedidoMapper::toResponse)
               .toList();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        pedidosResponse.forEach(pedido -> {
            try {
                // Serializa para JSON
                String jsonPagamentos = objectMapper.writeValueAsString(pedido.getPagamentosParciais());
                pedido.setJsonPagamentosParciais(jsonPagamentos);
            } catch (Exception e) {
                pedido.setJsonPagamentosParciais("[]"); // Caso dê erro, envia um array vazio
            }
        });
        
        model.addAttribute("pedidos", pedidosResponse);
        model.addAttribute("pedidoRequest", new PedidoRequest());
        model.addAttribute("clientes", clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
        model.addAttribute("cimentos", cimentoRepository.findAll(Sort.by(Sort.Direction.ASC, "marca")));
        return "pedidos/listar-pedido";
    }

    @PostMapping("/pedido/atualizar/{id}")
    public String atualizarPedido(@PathVariable Long id, @ModelAttribute PedidoRequest pedidoRequest) {
        pedidoService.atualizarPedido(id, pedidoRequest);
        return "redirect:/pedido/listar";
    }


    @GetMapping("/pedido/deletar/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

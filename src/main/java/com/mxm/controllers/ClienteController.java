package com.mxm.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mxm.dto.PedidoResponse;
import com.mxm.entity.Cliente;
import com.mxm.helpers.PedidoMapper;
import com.mxm.services.CidadeService;
import com.mxm.services.ClienteService;
import com.mxm.services.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final CidadeService cidadeService;
    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    // Listar todos os clientes
    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("cidades", cidadeService.listarTodos());
        return "clientes/listar-cliente";
    }

    // Exibir formulário de cadastro
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("cidades", cidadeService.listarTodos());
        return "clientes/cadastrar-cliente";
    }

    // Salvar um novo cliente
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cidades", cidadeService.listarTodos());
            return "clientes/cadastrar-cliente";
        }
        clienteService.salvar(cliente);
        return "redirect:/clientes";
    }

    // Buscar cliente para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("cidades", cidadeService.listarTodos());
        return "clientes/cadastrar-cliente";
    }

    // Atualizar cliente existente
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cidades", cidadeService.listarTodos());
            return "cadastrar-cliente";
        }
        clienteService.atualizar(id, cliente);
        return "redirect:/clientes";
    }

    // Excluir cliente
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/clientes";
    }
    
    @GetMapping("/{id}/pedidos")
    @ResponseBody
    public ResponseEntity<List<PedidoResponse>> listarPedidosPorCliente(@PathVariable Long id) {
        var pedidos = pedidoService.buscaPorCliente(id);
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
        return ResponseEntity.ok(pedidosResponse);
    }

}

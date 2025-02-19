package com.mxm.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mxm.dto.PedidoRequest;
import com.mxm.dto.PedidoResponse;
import com.mxm.entity.Pedido;
import com.mxm.helpers.PedidoMapper;
import com.mxm.repository.CidadeRepository;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.ClienteRepository;
import com.mxm.services.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final ClienteRepository clienteRepository;
    private final CidadeRepository cidadeRepository;
    private final CimentoRepository cimentoRepository;
    
    @GetMapping("/cadastrar")
    public String showForm(Model model) {
    	model.addAttribute("pedidoRequest", new PedidoRequest());
    	model.addAttribute("clientes", clienteRepository.findAll());
    	model.addAttribute("cidades", cidadeRepository.findAll());
        model.addAttribute("cimentos", cimentoRepository.findAll());
    	return "pedidos/cadastrar-pedido";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PedidoResponse> criarPedido(@Valid PedidoRequest pedidoRequest) {
    	Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
        Pedido novoPedido = pedidoService.criarPedido(pedido);

        PedidoResponse pedidoResponse = pedidoMapper.toResponse(novoPedido);

        return new ResponseEntity<>(pedidoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedidos/listar-pedido";
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        model.addAttribute("pedidoRequest", pedido);
        model.addAttribute("cidades", cidadeRepository.findAll());
        model.addAttribute("cimentos", cimentoRepository.findAll());
        return "pedidos/cadastrar-pedido";
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoRequest pedidoRequest) {
        Pedido pedidoAtualizado = pedidoMapper.toEntity(pedidoRequest);
        Pedido pedido = pedidoService.atualizarPedido(id, pedidoAtualizado);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @GetMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

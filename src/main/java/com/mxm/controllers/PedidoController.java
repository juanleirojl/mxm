package com.mxm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mxm.entity.Pedido;
import com.mxm.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  @Autowired
  private PedidoService pedidoService;

  @PostMapping
  public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
    Pedido novoPedido = pedidoService.criarPedido(pedido);
    return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
  }
}


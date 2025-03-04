package com.mxm.licitacao.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxm.licitacao.controllers.request.TransacaoRequest;
import com.mxm.licitacao.controllers.response.TransacaoResponse;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.mapper.TransacaoMapper;
import com.mxm.licitacao.services.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final TransacaoMapper transacaoMapper;

    public TransacaoController(TransacaoService transacaoService, TransacaoMapper transacaoMapper) {
        this.transacaoService = transacaoService;
        this.transacaoMapper = transacaoMapper;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> criarTransacao(@RequestBody TransacaoRequest request) {
        Transacao transacao = transacaoMapper.toEntity(request);
        transacao = transacaoService.criarTransacao(transacao);
        return ResponseEntity.ok(transacaoMapper.toResponse(transacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoResponse> editarTransacao(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        Transacao transacao = transacaoMapper.toEntity(request);
        transacao = transacaoService.editarTransacao(id, transacao);
        return ResponseEntity.ok(transacaoMapper.toResponse(transacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTransacao(@PathVariable Long id) {
        transacaoService.excluirTransacao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listarTransacoes() {
        List<Transacao> transacoes = transacaoService.listarTransacoes();
        List<TransacaoResponse> response = transacoes.stream()
            .map(transacaoMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponse> buscarTransacaoPorId(@PathVariable Long id) {
        return transacaoService.buscarTransacaoPorId(id)
            .map(transacaoMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

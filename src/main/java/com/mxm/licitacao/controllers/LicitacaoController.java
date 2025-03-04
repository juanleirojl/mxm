package com.mxm.licitacao.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxm.licitacao.controllers.request.LicitacaoRequest;
import com.mxm.licitacao.controllers.response.LicitacaoResponse;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.mapper.LicitacaoMapper;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/licitacoes")
@RequiredArgsConstructor
public class LicitacaoController {
    private final LicitacaoService licitacaoService;
    private final LicitacaoMapper licitacaoMapper;
    private final AutenticacaoService autenticacaoService;

    @PostMapping
    public ResponseEntity<LicitacaoResponse> criarLicitacao(@RequestBody LicitacaoRequest request) {
        Licitacao licitacao = licitacaoMapper.toEntity(request);
        licitacao = licitacaoService.criarLicitacao(licitacao);
        return ResponseEntity.ok(licitacaoMapper.toResponse(licitacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicitacaoResponse> editarLicitacao(@PathVariable Long id, @RequestBody LicitacaoRequest request) {
        Licitacao licitacao = licitacaoMapper.toEntity(request);
        licitacao = licitacaoService.editarLicitacao(id, licitacao);
        return ResponseEntity.ok(licitacaoMapper.toResponse(licitacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLicitacao(@PathVariable Long id) {
        licitacaoService.excluirLicitacao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LicitacaoResponse>> listarLicitacoes() {
        List<Licitacao> licitacoes = licitacaoService.listarLicitacoes();
        List<LicitacaoResponse> response = licitacoes.stream()
            .map(licitacaoMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicitacaoResponse> buscarLicitacaoPorId(@PathVariable Long id) {
        return licitacaoService.buscarLicitacaoPorId(id)
            .map(licitacaoMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/escolher-licitacao")
    public String escolherLicitacao(Model model) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();

        List<Licitacao> licitacoes;
        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"))) {
            // 🔹 ADMIN pode acessar todas as licitações
            licitacoes = licitacaoService.listarLicitacoes();
        } else {
            // 🔹 Usuário comum só acessa suas licitações
            Set<String> nomesLicitacoesPermitidas = usuario.getPerfis().stream()
                    .filter(perfil -> perfil.getNome().startsWith("LICITACAO_"))
                    .map(perfil -> perfil.getNome().replace("LICITACAO_", ""))
                    .collect(Collectors.toSet());

            licitacoes = licitacaoService.listarLicitacoesPorNomeNaLista(nomesLicitacoesPermitidas);
        }

        model.addAttribute("licitacoes", licitacoes);
        return "licitacao/escolher-licitacao";
    }
}
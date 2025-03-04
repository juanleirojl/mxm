package com.mxm.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final AutenticacaoService autenticacaoService;
    private final LicitacaoService licitacaoService;

    @ModelAttribute("licitacoesPermitidas")
    public List<Licitacao> carregarLicitacoesPermitidas(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return List.of(); // 🔹 Retorna lista vazia se não estiver autenticado
        }

        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        List<Licitacao> licitacoesPermitidas;

        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"))) {
            licitacoesPermitidas = licitacaoService.listarLicitacoes();
        } else {
            Set<String> nomesLicitacoesPermitidas = usuario.getPerfis().stream()
                    .filter(perfil -> perfil.getNome().startsWith("LICITACAO_"))
                    .map(perfil -> perfil.getNome().replace("LICITACAO_", ""))
                    .collect(Collectors.toSet());

            licitacoesPermitidas = licitacaoService.listarLicitacoesPorNomeNaLista(nomesLicitacoesPermitidas);
        }

        return licitacoesPermitidas;
    }
}

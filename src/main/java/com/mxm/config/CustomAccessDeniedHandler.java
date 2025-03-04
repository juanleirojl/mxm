package com.mxm.config;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final AutenticacaoService autenticacaoService;
    private final LicitacaoService licitacaoService;

    public CustomAccessDeniedHandler(AutenticacaoService autenticacaoService, LicitacaoService licitacaoService) {
        this.autenticacaoService = autenticacaoService;
        this.licitacaoService = licitacaoService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

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

        String destinoDashboard = licitacoesPermitidas.isEmpty() ? "/dashboard" : "/licitacao-dashboard/" + licitacoesPermitidas.get(0).getId();

        // 🔹 Armazena o `destinoDashboard` como atributo da requisição e redireciona para `403.html`
        request.setAttribute("destinoDashboard", destinoDashboard);
        request.getRequestDispatcher("/403").forward(request, response);
    }
}

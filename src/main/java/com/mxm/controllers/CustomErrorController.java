package com.mxm.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/error")
@Slf4j
public class CustomErrorController implements ErrorController {

    private final AutenticacaoService autenticacaoService;
    private final LicitacaoService licitacaoService;

    public CustomErrorController(AutenticacaoService autenticacaoService, LicitacaoService licitacaoService) {
        this.autenticacaoService = autenticacaoService;
        this.licitacaoService = licitacaoService;
    }

    @GetMapping
    public String handleError(HttpServletRequest request, Authentication authentication, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        // Verifica se o usuário está autenticado e obtém seus perfis
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

        // Verifica se o usuário tem alguma licitação vinculada
        String destinoDashboard = licitacoesPermitidas.isEmpty() ? "/dashboard" : "/licitacao-dashboard/" + licitacoesPermitidas.get(0).getId();
        model.addAttribute("destinoDashboard", destinoDashboard);
        log.info(destinoDashboard);
        // Redirecionamento para páginas específicas conforme o erro
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "redirect:" + destinoDashboard; // 🔹 Redireciona automaticamente para a licitação do usuário
            } else if (statusCode == 404) {
                return "404"; // 🔹 Agora Thymeleaf recebe o destino correto
            }
        }
        return "error"; // Página genérica de erro (caso queira criar uma)
    }
}

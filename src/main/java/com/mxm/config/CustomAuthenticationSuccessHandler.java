package com.mxm.config;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.services.LicitacaoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AutenticacaoService autenticacaoService;
    private final LicitacaoService licitacaoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        
        
        // 🔹 Se for CIMENTO, redireciona para o Dashboard
        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("CIMENTO"))) {
            response.sendRedirect("/dashboard");
            return;
        }

        // 🔹 Se for ADMIN, pode acessar todas as licitações
        List<Licitacao> licitacoesPermitidas;
        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"))) {
            licitacoesPermitidas = licitacaoService.listarLicitacoes();
        } else {
            // 🔹 Se não for ADMIN, busca apenas as licitações associadas aos seus perfis
            Set<String> nomesLicitacoesPermitidas = usuario.getPerfis().stream()
                    .filter(perfil -> perfil.getNome().startsWith("LICITACAO_"))
                    .map(perfil -> perfil.getNome().replace("LICITACAO_", ""))
                    .collect(Collectors.toSet());

            licitacoesPermitidas = licitacaoService.listarLicitacoesPorNomeNaLista(nomesLicitacoesPermitidas);
        }

        // 🔹 Se o usuário só tem acesso a uma licitação, redireciona automaticamente
        if (licitacoesPermitidas.size() == 1) {
            response.sendRedirect("/licitacao-dashboard/" + licitacoesPermitidas.get(0).getId());
            return;
        }

        // 🔹 Se tem múltiplas licitações, redireciona para a página de seleção
        response.sendRedirect("/licitacoes/escolher-licitacao");
    }
}

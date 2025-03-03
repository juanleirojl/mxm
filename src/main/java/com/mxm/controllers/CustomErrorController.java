package com.mxm.controllers;

import java.util.Collection;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Authentication authentication, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        // Verifica se o usuário está autenticado e obtém o perfil
        String perfil = "DEFAULT";
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                perfil = authority.getAuthority();
            }
        }

        // Redirecionamento para páginas específicas conforme o erro
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "403"; // Redireciona para a página 403.html
            } else if (statusCode == 404) {
                // Envia o perfil do usuário para o template Thymeleaf
                model.addAttribute("perfil", perfil);
                return "404"; // Redireciona para a página 404.html
            }
        }
        return "error"; // Página genérica de erro (caso queira criar uma)
    }
}

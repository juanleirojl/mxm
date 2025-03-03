package com.mxm.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mxm.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomeController {
	
	private final UsuarioService usuarioService;

    @GetMapping("/")
    public String redirecionarParaDashboard() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Deve corresponder ao nome do arquivo login.html
    }
    
    @GetMapping("/403")
    public String acessoNegado() {
        return "403";
    }
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    
 // Exibir a página de alteração de senha
    @GetMapping("/alterar-senha")
    public String mostrarPaginaAlterarSenha() {
        return "alterar-senha";
    }
    
 // Processar a alteração de senha
    @PostMapping("/alterar-senha")
    public String alterarSenha(@RequestParam("username") String username,
                               @RequestParam("novaSenha") String novaSenha,
                               @RequestParam("confirmarSenha") String confirmarSenha,
                               RedirectAttributes redirectAttributes) {
        try {
            usuarioService.alterarSenha(username, novaSenha, confirmarSenha);
            SecurityContextHolder.clearContext(); // 🔹 Faz logout do usuário após a alteração da senha
            redirectAttributes.addFlashAttribute("successMessage", "Senha alterada com sucesso! Faça login novamente.");
            return "redirect:/login"; // Redireciona para a página de login após o logout
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/alterar-senha"; // Redireciona de volta para a página de alteração de senha
        }
    }

}

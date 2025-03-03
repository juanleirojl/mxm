package com.mxm.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mxm.models.Perfil;
import com.mxm.models.Usuario;
import com.mxm.repository.PerfilRepository;
import com.mxm.services.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService usuarioService, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<Perfil> perfis = perfilRepository.findAll(); // Buscar todos os perfis disponíveis

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("perfis", perfis); // Adiciona a lista de perfis para o Thymeleaf

        return "usuarios/listar-usuarios"; // Página listar-usuarios.html
    }


    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                @RequestParam("perfis") List<Long> perfisIds,
                                @RequestParam("foto") MultipartFile foto,
                                RedirectAttributes redirectAttributes) throws IOException {
        try {
            List<Perfil> perfis = perfilRepository.findAllById(perfisIds);
            usuario.setPerfis(perfis);
            
            if (!foto.isEmpty()) {
                String fotoUrl = usuarioService.salvarFoto(foto);
                usuario.setFotoUrl(fotoUrl);
            }

            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
            return "redirect:/usuarios";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage()); // 🔹 Retorna a mensagem de erro
            return "redirect:/usuarios"; // 🔹 Redireciona de volta para a página de usuários
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id,
                                @ModelAttribute Usuario usuario,
                                @RequestParam("perfis") List<Long> perfisIds,
                                @RequestParam("foto") MultipartFile foto) throws IOException {
        
        usuarioService.atualizar(id, usuario, perfisIds, foto);
        
        return "redirect:/usuarios";
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }
}

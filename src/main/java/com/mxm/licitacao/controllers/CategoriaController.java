package com.mxm.licitacao.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mxm.licitacao.controllers.request.CategoriaRequest;
import com.mxm.licitacao.entity.Categoria;
import com.mxm.licitacao.mapper.CategoriaMapper;
import com.mxm.licitacao.services.CategoriaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    /** 📌 Listar todas as categorias */
    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("categorias", categorias);
        return "licitacao/categorias/listar-categorias"; // 🔹 Agora retorna a página correta para Thymeleaf
    }

    /** 📌 Criar nova categoria */
    @PostMapping("/salvar")
    public String criarCategoria(@ModelAttribute CategoriaRequest request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        categoriaService.criarCategoria(categoria, request.getCategoriaPaiId());
        return "redirect:/categorias"; // 🔹 Redireciona para a listagem após salvar
    }


    /** 📌 Atualizar categoria existente */
    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id, @ModelAttribute CategoriaRequest request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        categoriaService.editarCategoria(id, categoria);
        return "redirect:/categorias";
    }

    /** 📌 Excluir categoria com tratamento de erro */
    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.excluirCategoria(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria excluída com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/categorias";
    }

}

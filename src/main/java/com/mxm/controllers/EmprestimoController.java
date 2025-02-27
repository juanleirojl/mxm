package com.mxm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mxm.entity.Emprestimo;
import com.mxm.services.EmprestimoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @GetMapping
    public String listarEmprestimos(Model model) {
        model.addAttribute("emprestimos", emprestimoService.listarTodos());
        return "emprestimos/listar-emprestimos";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        return "emprestimos/cadastrar-emprestimo";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Emprestimo emprestimo, BindingResult result) {
        if (result.hasErrors()) {
            return "emprestimos/cadastrar-emprestimo";
        }
        emprestimoService.salvar(emprestimo);
        return "redirect:/emprestimos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("emprestimo", emprestimoService.buscarPorId(id));
        return "emprestimos/cadastrar-emprestimo";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Emprestimo emprestimo) {
        Emprestimo existente = emprestimoService.buscarPorId(id);
        existente.setValorEmprestado(emprestimo.getValorEmprestado());
        existente.setValorPago(emprestimo.getValorPago());
        existente.setDataEmprestimo(emprestimo.getDataEmprestimo());
        existente.setObservacao(emprestimo.getObservacao());
        emprestimoService.salvar(existente);
        return "redirect:/emprestimos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        emprestimoService.deletar(id);
        return "redirect:/emprestimos";
    }
}

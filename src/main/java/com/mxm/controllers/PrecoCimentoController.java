package com.mxm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mxm.dto.OrcamentoCimentoRequest;
import com.mxm.dto.PrecoCimentoRequest;
import com.mxm.repository.CidadeRepository;
import com.mxm.repository.CimentoRepository;
import com.mxm.repository.EmpresaRepository;
import com.mxm.services.PrecoCimentoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("preco")
@RequiredArgsConstructor
public class PrecoCimentoController {

  private final EmpresaRepository empresaRepository;
  private final CidadeRepository cidadeRepository;
  private final CimentoRepository cimentoRepository;
  private final PrecoCimentoService precoCimentoService;


  @GetMapping("/listar")
  public String listarOrcamentos(Model model) {
    // model.addAttribute("precosCimento", precoCimentoService.listar());
    model.addAttribute("precos", precoCimentoService.buscarPrecosConsolidados());
    return "preco-cimento/listar";
  }

  @GetMapping("/cadastrar")
  public String exibirCadastro(Model model) {
    model.addAttribute("empresas", empresaRepository.findAll());
    model.addAttribute("cidades", cidadeRepository.findAll());
    model.addAttribute("cimentos", cimentoRepository.findAll());
    model.addAttribute("preco", new PrecoCimentoRequest());
    return "preco-cimento/cadastrar"; // Nome da view Thymeleaf
  }

  @PostMapping("/cadastrar")
  public String cadastrarPreco(@ModelAttribute OrcamentoCimentoRequest request) {
    return "redirect:/preco-cimento/listar";
  }


}


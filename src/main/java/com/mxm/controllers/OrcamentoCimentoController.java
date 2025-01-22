package com.mxm.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mxm.dto.OrcamentoCimentoByNameRequest;
import com.mxm.dto.OrcamentoCimentoRequest;
import com.mxm.dto.OrcamentoCimentoResponse;
import com.mxm.services.CidadeService;
import com.mxm.services.CimentoService;
import com.mxm.services.ClienteService;
import com.mxm.services.EmpresaService;
import com.mxm.services.OrcamentoCimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/orcamentos")
@RequiredArgsConstructor
public class OrcamentoCimentoController {
  
  private final EmpresaService empresaService;
  private final ClienteService clienteService;
  private final CimentoService cimentoService;
  private final CidadeService cidadeService;
  private final OrcamentoCimentoService orcamentoCimentoService;

  @PostMapping("teste")
  public ResponseEntity<OrcamentoCimentoResponse> criarOrcamento(@RequestBody @Valid OrcamentoCimentoRequest request) {
      OrcamentoCimentoResponse response = orcamentoCimentoService.criarOrcamento(request);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/novo")
  public String novoOrcamento(Model model) {
    model.addAttribute("empresas", empresaService.listarTodos());
    model.addAttribute("clientes", clienteService.listarTodos());
    model.addAttribute("cimentos", cimentoService.listarTodos());
    model.addAttribute("cidades", cidadeService.listarTodos());
    return "novo-orcamento";
  }
  
  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }
  
  @PostMapping("/novo")
  public ResponseEntity<OrcamentoCimentoResponse> criarOrcamentoPorNomes(
          @RequestBody OrcamentoCimentoByNameRequest request) {
      OrcamentoCimentoResponse response = orcamentoCimentoService.criarOrcamentoPorNomes(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


  @GetMapping("/listar")
  public String listarOrcamentos(Model model) {
      List<OrcamentoCimentoResponse> orcamentos = orcamentoCimentoService.listarOrcamentos();

      // Calcula o total de sacos por marca
      Map<String, Integer> totalSacosPorMarca = orcamentos.stream()
          .collect(Collectors.groupingBy(
              OrcamentoCimentoResponse::getCimento, 
              Collectors.summingInt(OrcamentoCimentoResponse::getQuantidadeSacos)
          ));

   // Define os ícones para cada marca
      Map<String, String> iconesPorMarca = Map.of(
          "Poty", "fas fa-cubes",
          "Votoran", "fas fa-industry",
          "Nacional", "fas fa-warehouse",
          "Faciment", "fas fa-truck-loading"
      );
      // Define o estilo de ícone para cada marca (ex: primary, success, etc.)
      Map<String, String> estilosPorMarca = Map.of(
          "Poty", "icon-primary",       // Estilo para Poty
          "Votoran", "icon-info",       // Estilo para Votoran
          "Nacional", "icon-success",   // Estilo para Nacional
          "Faciment", "icon-secondary"  // Estilo para Faciment
      );

      model.addAttribute("orcamentos", orcamentos);
      model.addAttribute("totaisPorMarca", totalSacosPorMarca);
      model.addAttribute("iconesPorMarca", iconesPorMarca);
      model.addAttribute("estilosPorMarca", estilosPorMarca);

      return "listar-orcamentos";
  }


  
  @PostMapping
  public String salvarOrcamento(@ModelAttribute OrcamentoCimentoRequest orcamento) {
      // Salvar o orçamento no banco
      return "redirect:/orcamentos/listar";
  }

}

package com.mxm.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mxm.dto.termo.TermoCarregamentoRequest;
import com.mxm.entity.TermoCarregamento;
import com.mxm.helpers.DateFormatter;
import com.mxm.services.TermoCarregamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/termo")
@Slf4j
@RequiredArgsConstructor
public class TermoCarregamentoController {
  
  private final TermoCarregamentoService termoCarregamentoService;
  
  @GetMapping
  public String showForm(Model model) {
      model.addAttribute("termoCarregamentoRequest", new TermoCarregamentoRequest());
      return "termo/cadastrar-termo";
  }
  
  @GetMapping("/listar")
  public String listar(Model model) {
      model.addAttribute("termos", termoCarregamentoService.listar());
      return "termo/listar-termo";
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<byte[]> cadastrar(@ModelAttribute TermoCarregamentoRequest request) {
    log.info(request.toString());
    ResponseEntity<byte[]> arquivo = generateDocument(request);
    termoCarregamentoService.cadastrar(request, arquivo);
    return arquivo;
  }

  public ResponseEntity<byte[]> generateDocument(TermoCarregamentoRequest request) {
      try {
        
        String motorista = request.getMotorista();
        String data = request.getData();
        String placa = request.getPlaca();
        Long numeroPedido = request.getNumeroPedido();
        int quantidadeSacos = request.getQuantidadeSacos().intValue();
          // Converte a data para extenso
          String dataExtenso = DateFormatter.formatToLongDate(data);

          // Substituições no template
          Map<String, String> placeholders = new HashMap<>();
          placeholders.put("<MOTORISTA>", motorista);
          placeholders.put("<PLACA>", placa);
          placeholders.put("<QUANTIDADE>", String.valueOf(quantidadeSacos));
          placeholders.put("<PEDIDO>", numeroPedido.toString());
          placeholders.put("<DATA>", dataExtenso);

          // Caminho do template
          String templatePath = "src/main/resources/TERMO_DE_CARREGAMENTO_TEMPLATE.docx";

          // Processa o template
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          try (FileInputStream fis = new FileInputStream(templatePath);
               XWPFDocument document = new XWPFDocument(fis)) {

              for (var paragraph : document.getParagraphs()) {
                  for (var run : paragraph.getRuns()) {
                      String text = run.getText(0);
                      if (text != null) {
                          for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                              text = text.replace(entry.getKey(), entry.getValue());
                          }
                          run.setText(text, 0);
                      }
                  }
              }
              document.write(outputStream);
          }

          // Gera o nome do arquivo com base no motorista e na placa
          String fileName = String.format("TERMO_DE_CARREGAMENTO_%s_%s.docx", 
                                           motorista.replaceAll("\\s+", "_").toUpperCase(), 
                                           placa.toUpperCase());

          // Prepara o arquivo para download
          byte[] bytes = outputStream.toByteArray();
          HttpHeaders headers = new HttpHeaders();
          headers.add("Content-Disposition", "attachment; filename=" + fileName);

          return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
  }
  
  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> downloadArquivo(@PathVariable Long id) {
      TermoCarregamento termo = termoCarregamentoService.findById(id);

      if (termo.getArquivo() == null) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      String fileName = String.format("TERMO_DE_CARREGAMENTO_%s_%s.docx", 
                                       termo.getMotorista().replaceAll("\\s+", "_").toUpperCase(), 
                                       termo.getPlaca().toUpperCase());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=" + fileName);

      return new ResponseEntity<>(termo.getArquivo(), headers, HttpStatus.OK);
  }


}


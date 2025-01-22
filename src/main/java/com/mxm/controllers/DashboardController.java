package com.mxm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {
  
  
  @GetMapping(value = {"/dashboard","/"})
  public String dashboard() {
    return "dashboard";
  }
  
  @GetMapping("/orcamento")
  public String orcamento() {
    return "listar-orcamentos";
  }
  
  
}

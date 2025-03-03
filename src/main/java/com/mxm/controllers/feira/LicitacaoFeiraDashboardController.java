package com.mxm.controllers.feira;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/licitacao-feira-dashboard")
public class LicitacaoFeiraDashboardController {

    @GetMapping
    public String exibirDashboardLicitacao() {
        return "licitacao-feira/licitacao-feira-dashboard"; // Retorna o template licitacao-feira-dashboard.html
    }
}

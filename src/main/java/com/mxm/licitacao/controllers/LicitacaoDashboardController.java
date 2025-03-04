package com.mxm.licitacao.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxm.licitacao.controllers.response.LicitacaoDashboardResponse;
import com.mxm.licitacao.dto.CategoriaGastoDTO;
import com.mxm.licitacao.dto.FluxoCaixaDTO;
import com.mxm.licitacao.dto.FluxoMensalDTO;
import com.mxm.licitacao.dto.FornecedorGastoDTO;
import com.mxm.licitacao.dto.MetodoPagamentoDTO;
import com.mxm.licitacao.dto.ReceitaFonteDTO;
import com.mxm.licitacao.dto.SaldoMensalDTO;
import com.mxm.licitacao.dto.TipoGastoDTO;
import com.mxm.licitacao.dto.TransacaoResumoDTO;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.services.LicitacaoDashboardService;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;

import lombok.RequiredArgsConstructor;

@Controller // 🔹 Agora Thymeleaf pode renderizar as páginas HTML
@RequestMapping("/licitacao-dashboard")
@RequiredArgsConstructor
public class LicitacaoDashboardController {

    private final LicitacaoDashboardService dashboardService;
    private final LicitacaoService licitacaoService;
    private final AutenticacaoService autenticacaoService;

    /** 📌 1️⃣ Resumo Financeiro */
    @GetMapping("/resumo")
    @ResponseBody
    public ResponseEntity<LicitacaoDashboardResponse> getResumoFinanceiro(@RequestParam(required = false) Long licitacaoId) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        return ResponseEntity.ok(dashboardService.getDashboardData(usuario, licitacaoId));
    }

    /** 📌 2️⃣ Últimas Transações */
    @GetMapping("/ultimas-transacoes")
    @ResponseBody
    public ResponseEntity<List<TransacaoResumoDTO>> getUltimasTransacoes(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) Long licitacaoId) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        return ResponseEntity.ok(dashboardService.getUltimasTransacoes(usuario, licitacaoId, limit));
    }

    /** 📌 3️⃣ Categorias Mais Gastas */
    @GetMapping("/categorias-mais-gastas")
    @ResponseBody
    public ResponseEntity<List<CategoriaGastoDTO>> getCategoriasMaisGastas(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) Long licitacaoId) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        return ResponseEntity.ok(dashboardService.getCategoriasMaisGastas(usuario, licitacaoId, limit));
    }

    /** 📌 4️⃣ Fluxo de Caixa */
    @GetMapping("/fluxo-caixa")
    @ResponseBody
    public ResponseEntity<List<FluxoCaixaDTO>> getFluxoCaixa(@RequestParam(required = false) Long licitacaoId) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        return ResponseEntity.ok(dashboardService.getFluxoCaixa(usuario, licitacaoId));
    }
    
    /** 📌 5️⃣ Fornecedores Mais Pagos */
    @GetMapping("/fornecedores-mais-pagos")
    @ResponseBody
    public ResponseEntity<List<FornecedorGastoDTO>> getFornecedoresMaisPagos(@RequestParam(required = false) Long licitacaoId) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();
        return ResponseEntity.ok(dashboardService.getFornecedoresMaisPagos(usuario, licitacaoId));
    }
    
    

    /** 📌 6️⃣ Renderiza o Dashboard (Thymeleaf) */
    @GetMapping("/{id}")
    public String exibirDashboard(@PathVariable Long id, Model model) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();

        // 🔹 Busca a licitação pelo ID
        Licitacao licitacao = licitacaoService.buscarLicitacaoPorId(id)
                .orElseThrow(() -> new RuntimeException("Licitação não encontrada"));

        // 🔹 Verifica se o usuário tem permissão para acessar essa licitação
        boolean isAdmin = usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"));
        boolean temPermissao = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getNome().equals("LICITACAO_" + licitacao.getNome().toUpperCase()));

        if (!isAdmin && !temPermissao) {
            return "redirect:/403"; // Redireciona para a página de acesso negado
        }

        // 🔹 Adiciona os dados da licitação ao modelo para o Thymeleaf
        model.addAttribute("licitacao", licitacao);
        return "licitacao/licitacao-dashboard"; // 🔹 Agora o Thymeleaf renderiza corretamente
    }
    
    /** 📌 2️⃣ Saldo Mensal */
    @GetMapping("/saldo-mensal")
    @ResponseBody
    public ResponseEntity<List<SaldoMensalDTO>> getSaldoMensal(@RequestParam Long licitacaoId) {
        return ResponseEntity.ok(dashboardService.getSaldoMensal(licitacaoId));
    }

    /** 📌 3️⃣ Entradas vs Saídas */
    @GetMapping("/entradas-vs-saidas")
    @ResponseBody
    public ResponseEntity<List<FluxoMensalDTO>> getEntradasVsSaidas(@RequestParam Long licitacaoId) {
        return ResponseEntity.ok(dashboardService.getEntradasVsSaidas(licitacaoId));
    }

    /** 📌 4️⃣ Gastos por Categoria */
    @GetMapping("/tipos-gasto")
    @ResponseBody
    public ResponseEntity<List<TipoGastoDTO>> getTiposGasto(@RequestParam Long licitacaoId) {
        return ResponseEntity.ok(dashboardService.getTiposGasto(licitacaoId));
    }

    /** 📌 5️⃣ Métodos de Pagamento */
    @GetMapping("/metodos-pagamento")
    @ResponseBody
    public ResponseEntity<List<MetodoPagamentoDTO>> getMetodosPagamento(@RequestParam Long licitacaoId) {
        return ResponseEntity.ok(dashboardService.getMetodosPagamento(licitacaoId));
    }

    /** 📌 6️⃣ Receita por Fonte */
    @GetMapping("/receita-por-fonte")
    @ResponseBody
    public ResponseEntity<List<ReceitaFonteDTO>> getReceitaPorFonte(@RequestParam Long licitacaoId) {
        return ResponseEntity.ok(dashboardService.getReceitaPorFonte(licitacaoId));
    }

}

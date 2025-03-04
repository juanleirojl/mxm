package com.mxm.licitacao.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mxm.licitacao.controllers.request.LicitacaoRequest;
import com.mxm.licitacao.controllers.response.TransacaoResponse;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.mapper.LicitacaoMapper;
import com.mxm.licitacao.mapper.TransacaoMapper;
import com.mxm.licitacao.services.CategoriaService;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.licitacao.services.TransacaoService;
import com.mxm.models.Usuario;
import com.mxm.services.AutenticacaoService;
import com.mxm.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/licitacoes")
@RequiredArgsConstructor
public class LicitacaoController {
    private final LicitacaoService licitacaoService;
    private final TransacaoService transacaoService;
    private final LicitacaoMapper licitacaoMapper;
    private final TransacaoMapper transacaoMapper;
    private final AutenticacaoService autenticacaoService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    /** 📌 Listar todas as licitações */
    @GetMapping
    public String listarLicitacoes(Model model) {
        List<Licitacao> licitacoes = licitacaoService.listarLicitacoes();
        model.addAttribute("licitacoes", licitacoes);
        return "licitacao/listar-licitacoes"; // 🔹 Agora retorna a página Thymeleaf correta
    }

    /** 📌 Exibir formulário de cadastro */
    @GetMapping("/cadastrar")
    public String cadastrarLicitacao(Model model) {
        model.addAttribute("licitacao", new LicitacaoRequest());
        return "licitacao/cadastrar-licitacao"; // Criar essa página para exibir o formulário
    }

    /** 📌 Criar nova licitação */
    @PostMapping("/salvar")
    public String criarLicitacao(@ModelAttribute LicitacaoRequest request) {
        Licitacao licitacao = licitacaoMapper.toEntity(request);
        licitacaoService.criarLicitacao(licitacao);
        return "redirect:/licitacoes"; // 🔹 Redireciona para a listagem após salvar
    }

    /** 📌 Buscar licitação para edição */
    @GetMapping("/editar/{id}")
    public String editarLicitacao(@PathVariable Long id, Model model) {
        Licitacao licitacao = licitacaoService.buscarLicitacaoPorId(id)
                .orElseThrow(() -> new RuntimeException("Licitação não encontrada"));
        model.addAttribute("licitacao", licitacao);
        return "licitacao/cadastrar-licitacao"; // 🔹 Usa o mesmo formulário de cadastro para edição
    }

    /** 📌 Atualizar licitação existente */
    @PostMapping("/editar/{id}")
    public String atualizarLicitacao(@PathVariable Long id, @ModelAttribute LicitacaoRequest request) {
        Licitacao licitacao = licitacaoMapper.toEntity(request);
        licitacaoService.editarLicitacao(id, licitacao);
        return "redirect:/licitacoes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirLicitacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            licitacaoService.excluirLicitacao(id);
            redirectAttributes.addFlashAttribute("successMessage", "Licitação excluída com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/licitacoes";
    }


    /** 📌 Escolher licitação ao logar */
    @GetMapping("/escolher-licitacao")
    public String escolherLicitacao(Model model) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();

        List<Licitacao> licitacoes;
        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"))) {
            licitacoes = licitacaoService.listarLicitacoes();
        } else {
            Set<String> nomesLicitacoesPermitidas = usuario.getPerfis().stream()
                    .filter(perfil -> perfil.getNome().startsWith("LICITACAO_"))
                    .map(perfil -> perfil.getNome().replace("LICITACAO_", ""))
                    .collect(Collectors.toSet());

            licitacoes = licitacaoService.listarLicitacoesPorNomeNaLista(nomesLicitacoesPermitidas);
        }

        model.addAttribute("licitacoes", licitacoes);
        return "licitacao/escolher-licitacao";
    }
    
    /** 📌 Exibir detalhes de uma licitação */
    @GetMapping("/{id}")
    public String detalhesLicitacao(@PathVariable Long id, Model model) {
        Usuario usuario = autenticacaoService.getUsuarioAutenticado();

        Licitacao licitacao = licitacaoService.buscarLicitacaoPorId(id)
                .orElseThrow(() -> new RuntimeException("Licitação não encontrada"));

        // Verifica se o usuário tem permissão para ver essa licitação
        boolean isAdmin = usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"));
        boolean temPermissao = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getNome().equals("LICITACAO_" + licitacao.getNome().toUpperCase()));

        if (!isAdmin && !temPermissao) {
            return "redirect:/403";
        }

        // Carrega as transações relacionadas a essa licitação
        List<TransacaoResponse> transacoes = transacaoService.buscarPorLicitacaoId(id)
        		.stream().map(t -> transacaoMapper.toResponse(t))
        		.toList();

        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("licitacao", licitacao);
        model.addAttribute("transacoes", transacoes);
        return "licitacao/detalhes-licitacao";
    }
}

package com.mxm.licitacao.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mxm.licitacao.controllers.request.TransacaoRequest;
import com.mxm.licitacao.controllers.response.TransacaoResponse;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.mapper.LicitacaoMapper;
import com.mxm.licitacao.mapper.TransacaoMapper;
import com.mxm.licitacao.services.CategoriaService;
import com.mxm.licitacao.services.LicitacaoService;
import com.mxm.licitacao.services.TransacaoService;
import com.mxm.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;
    private final TransacaoMapper transacaoMapper;
    private final LicitacaoMapper licitacaoMapper;
    private final CategoriaService categoriaService;
    private final LicitacaoService licitacaoService;
    private final UsuarioService usuarioService;

    /** 📌 Listar todas as transações */
    @GetMapping
    public String listarTransacoes(Model model) {
        model.addAttribute("transacoes", transacaoService.listarTransacoes());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("licitacoes", licitacaoService.listarLicitacoes().stream().map(licitacaoMapper::toResponse).toList());

       List<TransacaoResponse> transacoes = transacaoService.listarTransacoes()
       .stream().map(t -> transacaoMapper.toResponse(t))
		.toList();
        
        model.addAttribute("transacoes", transacoes);
        return "licitacao/transacoes/listar-transacoes"; // 🔹 Agora retorna a página correta para Thymeleaf
    }


    /** 📌 Criar nova transação */
    @PostMapping
    public String criarTransacao(@ModelAttribute TransacaoRequest request, RedirectAttributes redirectAttributes) {
    	try {
    		Transacao transacao = transacaoMapper.toEntity(request);
            transacaoService.criarTransacao(transacao);
            redirectAttributes.addFlashAttribute("successMessage", "Transação salva com sucesso!");
    	}catch (Exception e) {
    		redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a transação: " + e.getMessage());
		}
    	
    	return "redirect:/transacoes"; // 🔹 Redireciona para a listagem após salvar
        
    }

    @PostMapping("salvar")
    public String criarTransacaoLicitacao(@ModelAttribute TransacaoRequest request, RedirectAttributes redirectAttributes) {
        try {
            Transacao transacao = transacaoMapper.toEntity(request);
            transacaoService.criarTransacao(transacao);

            // 🔹 Adiciona mensagem de sucesso
            redirectAttributes.addFlashAttribute("successMessage", "Transação salva com sucesso!");

            return "redirect:/licitacoes/" + transacao.getLicitacao().getId();
        } catch (Exception e) {
            // 🔹 Adiciona mensagem de erro em caso de falha
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a transação: " + e.getMessage());

            return "redirect:/licitacoes/" + request.getLicitacaoId();
        }
    }
    
    @PostMapping("/editar/{id}")
    public String editarTransacaoLicitacao(@PathVariable Long id, @ModelAttribute TransacaoRequest request, RedirectAttributes redirectAttributes) {
        try {
            Transacao transacao = transacaoMapper.toEntity(request);
            transacaoService.editarTransacao(id, transacao);
            redirectAttributes.addFlashAttribute("successMessage", "Transação atualizada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/licitacoes/" + request.getLicitacaoId();
    }


    @PostMapping("/{id}")
    public String atualizarTransacao(@PathVariable Long id, @ModelAttribute TransacaoRequest request, RedirectAttributes redirectAttributes) {
        try {
            Transacao transacao = transacaoMapper.toEntity(request);
            transacaoService.editarTransacao(id, transacao);
            redirectAttributes.addFlashAttribute("successMessage", "Transação atualizada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/transacoes";
    }


    /** 📌 Excluir transação */
    @GetMapping("/excluir/{id}")
    public String excluirTransacao(@PathVariable Long id) {
        transacaoService.excluirTransacao(id);
        return "redirect:/transacoes";
    }
    
    /** 📌 Exibir detalhes de uma transação */
    @GetMapping("/{id}")
    public String detalhesTransacao(@PathVariable Long id, Model model) {
        // Filtrar transações que pertencem à mesma licitação
        List<TransacaoResponse> transacoesRelacionadas = transacaoService
                .buscarPorLicitacaoId(id)
                .stream()
                .map(transacaoMapper::toResponse)
                .collect(Collectors.toList());

        model.addAttribute("transacoesRelacionadas", transacoesRelacionadas);
        return "licitacao/transacoes/detalhes-transacao";
    }
    
    @PostMapping("/excluir/{id}")
    public String excluirTransacao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Transacao transacao = transacaoService.buscarTransacaoPorId(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));


        transacaoService.excluirTransacao(id);
        redirectAttributes.addFlashAttribute("successMessage", "Transação excluída com sucesso.");
        return "redirect:/licitacoes/" + transacao.getLicitacao().getId();
    }

}

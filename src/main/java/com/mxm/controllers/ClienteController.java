package com.mxm.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mxm.entity.Cliente;
import com.mxm.services.CidadeService;
import com.mxm.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final CidadeService cidadeService;

    // Lista todos os clientes e exibe na página de listagem
    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/listar-cliente"; // Renderiza clientes/listar.html
    }

    // Abre o formulário de cadastro
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("cidades", cidadeService.listarTodos());
        return "clientes/cadastrar-cliente"; // Renderiza clientes/cadastrar.html
    }

    // Salva o cliente no banco de dados
    @PostMapping
    public String salvar(@Valid @ModelAttribute Cliente cliente, 
                         BindingResult result, 
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cidades", List.of());
            return "clientes/cadastrar";
        }
        clienteService.salvar(cliente);
        return "redirect:/clientes";
    }

    // Busca cliente por ID e abre o formulário de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("cidades", cidadeService.listarTodos());
        return "clientes/cadastrar"; // Reutiliza o formulário para editar
    }

    // Atualiza o cliente após a edição
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente) {
        Cliente clienteExistente = clienteService.buscarPorId(id);
        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setResponsavel(cliente.getResponsavel());
        clienteExistente.setEndereco(cliente.getEndereco());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setCidade(cliente.getCidade());
        clienteService.salvar(clienteExistente);
        return "redirect:/clientes"; // Redireciona para a listagem após atualizar
    }

    // Exclui um cliente
    @GetMapping("/excluir/{id}")
    public String deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/clientes"; // Redireciona para a listagem após excluir
    }
}

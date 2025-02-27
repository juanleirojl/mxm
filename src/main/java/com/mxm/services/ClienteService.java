package com.mxm.services;

import com.mxm.entity.Cliente;
import com.mxm.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public void atualizar(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = buscarPorId(id);
        if (clienteExistente != null) {
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setResponsavel(clienteAtualizado.getResponsavel());
            clienteExistente.setEndereco(clienteAtualizado.getEndereco());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setCidade(clienteAtualizado.getCidade());
            clienteRepository.save(clienteExistente);
        }
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}

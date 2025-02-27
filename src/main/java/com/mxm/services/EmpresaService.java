package com.mxm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxm.entity.Empresa;
import com.mxm.repository.EmpresaRepository;

@Service
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

  public List<Empresa> listarTodos() {
    return empresaRepository.findAll();
  }

  public Empresa salvar(Empresa empresa) {
    return empresaRepository.save(empresa);
  }

  public Empresa buscarPorId(Long id) {
    return empresaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Empresa não encontrado!"));
  }

  public void deletar(Long id) {
    empresaRepository.deleteById(id);
  }
}

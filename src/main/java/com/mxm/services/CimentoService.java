package com.mxm.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxm.entity.Cimento;
import com.mxm.repository.CimentoRepository;

@Service
public class CimentoService {

  @Autowired
  private CimentoRepository cimentoRepository;

  public List<Cimento> listarTodos() {
    return cimentoRepository.findAll();
  }

  public Cimento salvar(Cimento cimento) {
    return cimentoRepository.save(cimento);
  }

  public Cimento buscarPorId(Long id) {
    return cimentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cimento não encontrado!"));
  }

  public void deletar(Long id) {
    cimentoRepository.deleteById(id);
  }
}

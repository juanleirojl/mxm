package com.mxm.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxm.entity.Cidade;
import com.mxm.repository.CidadeRepository;

@Service
public class CidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  public List<Cidade> listarTodos() {
    return cidadeRepository.findAll();
  }

  public Cidade salvar(Cidade cidade) {
    return cidadeRepository.save(cidade);
  }

  public Cidade buscarPorId(Long id) {
    return cidadeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cidade não encontrado!"));
  }

  public void deletar(Long id) {
    cidadeRepository.deleteById(id);
  }
}

package com.mxm.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.mxm.dto.PrecoConsolidadoResponse;
import com.mxm.entity.PrecoCimento;
import com.mxm.repository.PrecoCimentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrecoCimentoService {

  private final PrecoCimentoRepository precoCimentoRepository;
  
  public List<PrecoCimento> listar() {
    return precoCimentoRepository.findAll();
  }
  
  public List<PrecoConsolidadoResponse> buscarPrecosConsolidados() {
    return precoCimentoRepository.buscarPrecosConsolidados();
  }

}

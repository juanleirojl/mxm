package com.mxm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.TermoCarregamento;

public interface TermoCarregamentoRepository extends JpaRepository<TermoCarregamento, Long> {
  
  List<TermoCarregamento> findByMotorista(String motorista);
  
  List<TermoCarregamento> findByPlaca(String placa);
  
  List<TermoCarregamento> findByNumeroPedido(Long numeroPedido);

}

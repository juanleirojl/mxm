package com.mxm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mxm.dto.TermoCarregamentoDTO;
import com.mxm.entity.TermoCarregamento;

public interface TermoCarregamentoRepository extends JpaRepository<TermoCarregamento, Long> {
  
  List<TermoCarregamento> findByMotorista(String motorista);
  
  List<TermoCarregamento> findByPlaca(String placa);
  
  List<TermoCarregamento> findByNumeroPedido(Long numeroPedido);
  
  // Consulta para retornar apenas os campos necessários
  @Query("SELECT new com.mxm.dto.TermoCarregamentoDTO(t.id, t.motorista, t.placa, t.quantidadeSacos, t.numeroPedido, t.dataCadastro) " +
         "FROM TermoCarregamento t ORDER BY t.dataCadastro DESC")
  List<TermoCarregamentoDTO> findAllWithoutArquivo();

}

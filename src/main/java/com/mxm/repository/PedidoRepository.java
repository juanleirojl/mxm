package com.mxm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mxm.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

  @Query("SELECT p FROM Pedido p " +
      "WHERE (:clienteId IS NULL OR p.cliente.id = :clienteId) " +
      "AND (:cimentoId IS NULL OR p.cimento.id = :cimentoId) " +
      "AND (:dataDe IS NULL OR p.data >= :dataDe) " +
      "AND (:dataAte IS NULL OR p.data <= :dataAte) " +
      "ORDER BY p.data DESC")
  List<Pedido> buscarPedidosPorFiltros(@Param("clienteId") Long clienteId,
                                       @Param("cimentoId") Long cimentoId,
                                       @Param("dataDe") LocalDate dataDe,
                                       @Param("dataAte") LocalDate dataAte);
  
  
  List<Pedido> findByClienteId(Long clienteId);
}

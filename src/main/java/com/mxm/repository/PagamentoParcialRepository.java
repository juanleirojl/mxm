package com.mxm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.PagamentoParcial;

public interface PagamentoParcialRepository extends JpaRepository<PagamentoParcial, Long> {
    List<PagamentoParcial> findByPedidoId(Long pedidoId);
}

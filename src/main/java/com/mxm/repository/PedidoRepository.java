package com.mxm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}

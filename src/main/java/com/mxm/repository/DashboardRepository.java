package com.mxm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mxm.dto.PagamentosParciaisDTO;
import com.mxm.dto.PedidosPendentesDTO;
import com.mxm.dto.PedidosPorMotoristaDTO;
import com.mxm.dto.PedidosPorPlacaDTO;
import com.mxm.dto.VendasPorCimentoDTO;
import com.mxm.dto.VendasPorClienteDTO;
import com.mxm.dto.VendasPorMesDTO;
import com.mxm.entity.Emprestimo;
import com.mxm.entity.Pedido;

@Repository
public interface DashboardRepository extends CrudRepository<Pedido, Long> {

	@Query("""
	        SELECT new com.mxm.dto.VendasPorMesDTO(
	            CAST(YEAR(p.data) AS String),
	            CAST(MONTH(p.data) AS String),
	            COALESCE(SUM(p.quantidade), 0), 
	            COALESCE(SUM(p.precoCimentoVendido * p.quantidade), 0)
	        ) 
	        FROM Pedido p 
	        WHERE p.data >= :dataInicio 
	        GROUP BY YEAR(p.data), MONTH(p.data) 
	        ORDER BY YEAR(p.data), MONTH(p.data)
	    """)
	List<VendasPorMesDTO> buscarVendasPorMes(@Param("dataInicio") LocalDate dataInicio);


    // 🔹 Total de vendas por cliente
    @Query("SELECT new com.mxm.dto.VendasPorClienteDTO(p.cliente.nome, SUM(p.quantidade), SUM(p.precoCimentoVendido * p.quantidade)) " +
           "FROM Pedido p GROUP BY p.cliente.nome ORDER BY SUM(p.precoCimentoVendido * p.quantidade) DESC")
    List<VendasPorClienteDTO> buscarVendasPorCliente();

    // 🔹 Total de vendas por marca de cimento
    @Query("SELECT new com.mxm.dto.VendasPorCimentoDTO(p.cimento.marca, SUM(p.quantidade), SUM(p.precoCimentoVendido * p.quantidade)) " +
           "FROM Pedido p GROUP BY p.cimento.marca ORDER BY SUM(p.quantidade) DESC")
    List<VendasPorCimentoDTO> buscarVendasPorCimento();
    
 // 🔹 Pedidos Pendentes de Pagamento (Clientes que ainda não pagaram)
    @Query("""
    	    SELECT new com.mxm.dto.PedidosPendentesDTO(
    	        p.cliente.nome,
    	        COUNT(p.id),
    	        SUM(p.valorReceberCliente - COALESCE(p.valorParcial, 0))
    	    ) 
    	    FROM Pedido p 
    	    WHERE p.statusPagamento = 'PENDENTE' 
    	       OR (p.statusPagamento = 'PARCIAL' AND p.valorReceberCliente > COALESCE(p.valorParcial, 0))
    	    GROUP BY p.cliente.nome
    	""")
    	List<PedidosPendentesDTO> buscarPedidosPendentes();
    @Query("""
    	    SELECT new com.mxm.dto.PagamentosParciaisDTO(
    	        p.cliente.nome, 
    	        COALESCE(SUM(p.valorParcial), 0),
    	        COALESCE(SUM(p.valorReceberCliente - p.valorParcial), 0)
    	    ) 
    	    FROM Pedido p 
    	    WHERE p.statusPagamento = 'PARCIAL'
    	    GROUP BY p.cliente.nome
    	""")
    	List<PagamentosParciaisDTO> buscarPagamentosParciais();


    // 🔹 Placas com mais pedidos (Veículos que mais realizaram entregas)
    @Query("""
    	    SELECT new com.mxm.dto.PedidosPorPlacaDTO(
    	        COALESCE(p.placa, 'SEM PLACA'), 
    	        COUNT(p.id)
    	    ) 
    	    FROM Pedido p 
    	    GROUP BY p.placa
    	    ORDER BY COUNT(p.id) DESC
    	""")
    	List<PedidosPorPlacaDTO> buscarPedidosPorPlaca();


    @Query("""
    	    SELECT new com.mxm.dto.PedidosPorMotoristaDTO(
    	        COALESCE(p.motorista, 'SEM MOTORISTA'), 
    	        COUNT(p.id)
    	    ) 
    	    FROM Pedido p 
    	    GROUP BY p.motorista
    	    ORDER BY COUNT(p.id) DESC
    	""")
    	List<PedidosPorMotoristaDTO> buscarPedidosPorMotorista();
    
    @Query(value = """
    	    SELECT 
    	        c.nome AS cliente,
    	        p.valor_receber_cliente AS totalAPagar,
    	        COALESCE(p.valor_receber_cliente - p.valor_parcial, p.valor_receber_cliente) AS valorPendente,
    	        CAST(JULIANDAY(CURRENT_DATE) - JULIANDAY(p.data) AS INTEGER) AS diasAtraso
    	    FROM pedido p
    	    JOIN cliente c ON p.cliente_id = c.id
    	    WHERE p.status_pagamento = 'PENDENTE' 
    	       OR (p.status_pagamento = 'PARCIAL' AND p.valor_receber_cliente > COALESCE(p.valor_parcial, 0))
    	    ORDER BY p.data ASC
    	""", nativeQuery = true)
    	List<Object[]> buscarPedidosPendentesTabela();

    	@Query("SELECT e FROM Emprestimo e ORDER BY e.dataEmprestimo ASC")
    	List<Emprestimo> buscarEmprestimos();


}

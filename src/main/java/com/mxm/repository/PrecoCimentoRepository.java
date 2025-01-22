package com.mxm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mxm.dto.PrecoConsolidadoResponse;
import com.mxm.entity.PrecoCimento;

public interface PrecoCimentoRepository extends JpaRepository<PrecoCimento, Long> {

//  @Query("""
//      SELECT p
//      FROM PrecoCimento p
//      WHERE p.empresa.id = :empresaId
//      AND p.cimento.id = :cimentoId
//      AND p.cidade.id = :cidadeId
//      AND p.quantidadeSacos <= :quantidadeSacos
//      ORDER BY p.quantidadeSacos DESC
//  """)
//  Optional<PrecoCimento> buscaPrecoCimento(
//      @Param("empresaId") Long empresaId,
//      @Param("cimentoId") Long cimentoId,
//      @Param("cidadeId") Long cidadeId,
//      @Param("quantidadeSacos") Integer quantidadeSacos
//  );
  
  @Query("""
      SELECT p
      FROM PrecoCimento p
      WHERE p.empresa.id = :empresaId
      AND p.cimento.id = :cimentoId
      AND p.cidade.id = :cidadeId
      AND p.quantidadeSacos <= :quantidadeSacos
      ORDER BY p.quantidadeSacos DESC
      LIMIT 1
  """)
  Optional<PrecoCimento> buscaPrecoCimentoMaisProximo(
      @Param("empresaId") Long empresaId,
      @Param("cimentoId") Long cimentoId,
      @Param("cidadeId") Long cidadeId,
      @Param("quantidadeSacos") Integer quantidadeSacos
  );
  
  @Query("""
      SELECT p
      FROM PrecoCimento p
      WHERE p.empresa.id = :empresaId
        AND p.cimento.id = :cimentoId
        AND p.cidade.id = :cidadeId
        AND p.quantidadeSacos <= :quantidadeSacos
      ORDER BY p.quantidadeSacos DESC
  """)
  List<PrecoCimento> buscaMenorOuIgual(
      @Param("empresaId") Long empresaId,
      @Param("cimentoId") Long cimentoId,
      @Param("cidadeId") Long cidadeId,
      @Param("quantidadeSacos") Integer quantidadeSacos
  );
  
  @Query("""
      SELECT p
      FROM PrecoCimento p
      WHERE p.empresa.id = :empresaId
        AND p.cimento.id = :cimentoId
        AND p.cidade.id = :cidadeId
        AND p.quantidadeSacos > :quantidadeSacos
      ORDER BY p.quantidadeSacos ASC
  """)
  List<PrecoCimento> buscaMaior(
      @Param("empresaId") Long empresaId,
      @Param("cimentoId") Long cimentoId,
      @Param("cidadeId") Long cidadeId,
      @Param("quantidadeSacos") Integer quantidadeSacos
  );
  
  @Query("""
      SELECT p
      FROM PrecoCimento p
      WHERE p.empresa.id = :empresaId
        AND p.cimento.id = :cimentoId
        AND p.cidade.id = :cidadeId
      ORDER BY p.quantidadeSacos ASC
  """)
  List<PrecoCimento> findAllByEmpresaAndCimentoAndCidade(
      @Param("empresaId") Long empresaId,
      @Param("cimentoId") Long cimentoId,
      @Param("cidadeId") Long cidadeId
  );

  @Query("SELECT new com.mxm.dto.PrecoConsolidadoResponse(" +
      "empresa.nome, " +
      "empresa.cnpj, " +
      "cidade.nome, " +
      "CONCAT(cimento.tipo, ' - ', cimento.marca), " +
      "MAX(CASE WHEN p.quantidadeSacos = 280 THEN p.valor END), " +
      "MAX(CASE WHEN p.quantidadeSacos = 560 THEN p.valor END), " +
      "MAX(CASE WHEN p.quantidadeSacos = 720 THEN p.valor END)) " +
      "FROM PrecoCimento p " +
      "JOIN p.empresa empresa " +
      "JOIN p.cidade cidade " +
      "JOIN p.cimento cimento " +
      "GROUP BY empresa.id, cidade.id, cimento.id")
  List<PrecoConsolidadoResponse> buscarPrecosConsolidados();
}

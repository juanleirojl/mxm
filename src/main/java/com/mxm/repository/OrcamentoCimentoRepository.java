package com.mxm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.OrcamentoCimento;

public interface OrcamentoCimentoRepository extends JpaRepository<OrcamentoCimento, Long> {

  List<OrcamentoCimento> findByClienteId(Long clienteId);

}

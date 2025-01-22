package com.mxm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.Cimento;

public interface CimentoRepository extends JpaRepository<Cimento, Long> {

  Optional<Cimento> findByMarcaAndTipo(String string, String string2);

  Optional<Cimento> findByMarcaIgnoreCase(String cimento);

}

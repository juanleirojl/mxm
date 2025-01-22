package com.mxm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

  Optional<Cidade> findByNomeIgnoreCase(String string);


}

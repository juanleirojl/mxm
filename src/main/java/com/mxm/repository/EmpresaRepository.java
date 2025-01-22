package com.mxm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mxm.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

  Optional<Empresa> findByCnpj(String string);

  Optional<Empresa> findByNomeIgnoreCase(String empresa);

}

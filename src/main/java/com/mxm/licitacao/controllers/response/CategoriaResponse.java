package com.mxm.licitacao.controllers.response;

import com.mxm.licitacao.enums.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse {
    private Long id;
    private String nome;
    private TipoTransacao tipoTransacao;
    private Long categoriaPaiId;  // Permite buscar a relação pai-filho
    private String categoriaPaiNome;
}

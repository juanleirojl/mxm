package com.mxm.licitacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.mxm.licitacao.controllers.request.CategoriaRequest;
import com.mxm.licitacao.controllers.response.CategoriaResponse;
import com.mxm.licitacao.entity.Categoria;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    typeConversionPolicy = ReportingPolicy.ERROR
)
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoriaPai", source = "categoriaPaiId", qualifiedByName = "mapCategoriaPai")
    Categoria toEntity(CategoriaRequest request);

    @Mapping(target = "categoriaPaiNome", source = "categoriaPai.nome")
    @Mapping(target = "categoriaPaiId", source = "categoriaPai.id")
    CategoriaResponse toResponse(Categoria categoria);

    // Método auxiliar para mapear categoriaPaiId -> Categoria
    @Named("mapCategoriaPai")
    default Categoria mapCategoriaPai(Long categoriaPaiId) {
        if (categoriaPaiId == null) {
            return null;
        }
        return Categoria.builder().id(categoriaPaiId).build();
    }
}

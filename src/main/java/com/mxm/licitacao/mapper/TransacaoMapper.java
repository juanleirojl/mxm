package com.mxm.licitacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.mxm.licitacao.controllers.request.TransacaoRequest;
import com.mxm.licitacao.controllers.response.TransacaoResponse;
import com.mxm.licitacao.entity.Transacao;

@Component
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    typeConversionPolicy = ReportingPolicy.ERROR
)
public interface TransacaoMapper {

    TransacaoMapper INSTANCE = Mappers.getMapper(TransacaoMapper.class);

    @Mapping(target = "id", ignore = true) // O ID será gerado automaticamente
    @Mapping(target = "data", ignore = true) // Mapeando a data explicitamente
    @Mapping(target = "categoria", source = "categoriaId", ignore = true) // Precisamos buscar a Categoria no Service
    @Mapping(target = "licitacao", source = "licitacaoId", ignore = true) // Precisamos buscar a Licitação no Service
    @Mapping(target = "responsavel", source = "responsavelId", ignore = true) // Precisamos buscar o Responsável no Service
    Transacao toEntity(TransacaoRequest request);

    @Mapping(target = "data", source = "data") // Mapeia diretamente a data
    @Mapping(target = "categoriaNome", source = "categoria.nome")
    @Mapping(target = "licitacaoNome", source = "licitacao.nome")
    @Mapping(target = "responsavelNome", source = "responsavel.nome")
    TransacaoResponse toResponse(Transacao transacao);

}

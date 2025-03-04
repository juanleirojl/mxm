package com.mxm.licitacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxm.licitacao.controllers.request.TransacaoRequest;
import com.mxm.licitacao.controllers.response.TransacaoResponse;
import com.mxm.licitacao.entity.Categoria;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.repositories.CategoriaRepository;
import com.mxm.licitacao.repositories.LicitacaoRepository;
import com.mxm.models.Usuario;
import com.mxm.repository.UsuarioRepository;

@Component
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    typeConversionPolicy = ReportingPolicy.ERROR
)
public abstract class TransacaoMapper {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /** 📌 Mapeia TransacaoRequest -> Transacao */
    @Mapping(target = "id", ignore = true) // O ID será gerado automaticamente
    @Mapping(target = "data", ignore = true) // Data será gerada automaticamente
    //@Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "mapCategoria") // Converte categoriaId para Categoria
    @Mapping(target = "responsavel.id", source = "responsavelId")
    @Mapping(target = "categoria.id", source = "categoriaId")
    @Mapping(target = "licitacao.id", source = "licitacaoId")
    //@Mapping(target = "responsavel", source = "responsavelId", qualifiedByName = "mapResponsavel") // Converte responsavelId para Usuário
    public abstract Transacao toEntity(TransacaoRequest request);

    /** 📌 Mapeia Transacao -> TransacaoResponse */
    @Mapping(target = "data", source = "data") 
    @Mapping(target = "categoriaNome", source = "categoria.nome")
    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "licitacaoNome", source = "licitacao.nome")
    @Mapping(target = "licitacaoId", source = "licitacao.id")
    @Mapping(target = "responsavelNome", source = "responsavel.nome")
    @Mapping(target = "responsavelId", source = "responsavel.id")
    public abstract TransacaoResponse toResponse(Transacao transacao);

    /** 📌 Método Auxiliar para Buscar Categoria */
    @Named("mapCategoria")
    Categoria mapCategoria(Long categoriaId) {
        return categoriaId == null ? null : categoriaRepository.findById(categoriaId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    /** 📌 Método Auxiliar para Buscar Usuário Responsável */
    @Named("mapResponsavel")
    Usuario mapResponsavel(Long responsavelId) {
        return responsavelId == null ? null : usuarioRepository.findById(responsavelId)
            .orElseThrow(() -> new RuntimeException("Usuário responsável não encontrado"));
    }
}

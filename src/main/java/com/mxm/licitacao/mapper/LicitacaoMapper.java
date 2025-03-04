package com.mxm.licitacao.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.mxm.licitacao.controllers.request.LicitacaoRequest;
import com.mxm.licitacao.controllers.response.LicitacaoResponse;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.enums.TipoTransacao;

@Component
@Mapper(
	    componentModel = MappingConstants.ComponentModel.SPRING,
	    unmappedTargetPolicy = ReportingPolicy.ERROR,
	    typeConversionPolicy = ReportingPolicy.ERROR)
public interface LicitacaoMapper {

    LicitacaoMapper INSTANCE = Mappers.getMapper(LicitacaoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transacoes", ignore = true) // Evita carregar todas as transações (evita referência cíclica)
    Licitacao toEntity(LicitacaoRequest request);

    @Mapping(target = "totalEntradas", expression = "java(calcularTotalEntradas(licitacao))")
    @Mapping(target = "totalSaidas", expression = "java(calcularTotalSaidas(licitacao))")
    LicitacaoResponse toResponse(Licitacao licitacao);

    default BigDecimal calcularTotalEntradas(Licitacao licitacao) {
        return licitacao.getTransacoes().stream()
            .filter(t -> t.getTipoTransacao() == TipoTransacao.ENTRADA)
            .map(t -> t.getValor())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal calcularTotalSaidas(Licitacao licitacao) {
        return licitacao.getTransacoes().stream()
            .filter(t -> t.getTipoTransacao() == TipoTransacao.SAIDA)
            .map(t -> t.getValor())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
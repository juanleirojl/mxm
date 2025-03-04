package com.mxm.licitacao.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.controllers.response.LicitacaoDashboardResponse;
import com.mxm.licitacao.dto.CategoriaGastoDTO;
import com.mxm.licitacao.dto.FluxoCaixaDTO;
import com.mxm.licitacao.dto.FluxoMensalDTO;
import com.mxm.licitacao.dto.FornecedorGastoDTO;
import com.mxm.licitacao.dto.MetodoPagamentoDTO;
import com.mxm.licitacao.dto.ReceitaFonteDTO;
import com.mxm.licitacao.dto.SaldoAtualDTO;
import com.mxm.licitacao.dto.SaldoMensalDTO;
import com.mxm.licitacao.dto.TipoGastoDTO;
import com.mxm.licitacao.dto.TransacaoResumoDTO;
import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.enums.TipoTransacao;
import com.mxm.licitacao.repositories.LicitacaoRepository;
import com.mxm.licitacao.repositories.TransacaoRepository;
import com.mxm.models.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicitacaoDashboardService {

    private final LicitacaoRepository licitacaoRepository;
    private final TransacaoRepository transacaoRepository;

    /** 🔹 Obtém o Resumo Financeiro */
    public LicitacaoDashboardResponse getDashboardData(Usuario usuario, Long licitacaoId) {
        Licitacao licitacao = getLicitacaoUsuario(usuario, licitacaoId);

        BigDecimal totalEntradas = calcularTotal(licitacao, TipoTransacao.ENTRADA);
        BigDecimal totalSaidas = calcularTotal(licitacao, TipoTransacao.SAIDA);
        BigDecimal lucroPrejuizo = totalEntradas.subtract(totalSaidas);

        return new LicitacaoDashboardResponse(
                licitacao.getNome(),
                licitacao.getSaldoAtual(),
                totalEntradas,
                totalSaidas,
                lucroPrejuizo,
                licitacao.getTransacoes().size(),
                getUltimasTransacoes(usuario, licitacaoId, 5),
                getCategoriasMaisGastas(usuario, licitacaoId, 5),
                getFluxoCaixa(usuario, licitacaoId)
        );
    }

    /** 🔹 Obtém a Licitação do Usuário OU a Escolhida pelo Admin */
    private Licitacao getLicitacaoUsuario(Usuario usuario, Long licitacaoId) {
        // Se for ADMIN e um ID foi passado, ele pode ver qualquer licitação
        if (usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN")) && licitacaoId != null) {
            return licitacaoRepository.findById(licitacaoId)
                    .orElseThrow(() -> new RuntimeException("Licitação não encontrada."));
        }

        // Coletar todas as licitações que o usuário pode acessar
        Set<String> licitacoesPermitidas = usuario.getPerfis().stream()
                .filter(perfil -> perfil.getNome().startsWith("LICITACAO_"))
                .map(perfil -> perfil.getNome().replace("LICITACAO_", ""))
                .collect(Collectors.toSet());

        if (licitacaoId != null) {
            Licitacao licitacao = licitacaoRepository.findById(licitacaoId)
                    .orElseThrow(() -> new RuntimeException("Licitação não encontrada."));
            
            // Verificar se o usuário tem permissão para acessar essa licitação
            Set<String> licitacoesPermitidasLower = licitacoesPermitidas.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            if (!licitacoesPermitidasLower.contains(licitacao.getNome().toLowerCase())) {
                throw new RuntimeException("Usuário não tem permissão para acessar essa licitação.");
            }

            return licitacao;
        }

        // Se nenhum ID foi passado, pegar a primeira licitação permitida para o usuário
        return licitacaoRepository.findByNome(licitacoesPermitidas.iterator().next())
                .orElseThrow(() -> new RuntimeException("Nenhuma licitação encontrada para este usuário."));
    }

    /** 🔹 Obtém as Últimas Transações */
    public List<TransacaoResumoDTO> getUltimasTransacoes(Usuario usuario, Long licitacaoId, int limit) {
        Licitacao licitacao = getLicitacaoUsuario(usuario, licitacaoId);
        return licitacao.getTransacoes().stream()
                .sorted((a, b) -> b.getData().compareTo(a.getData()))
                .limit(limit)
                .map(t -> new TransacaoResumoDTO(t.getDescricao(), t.getValor(), t.getTipoTransacao(), t.getData()))
                .collect(Collectors.toList());
    }

    /** 🔹 Obtém as Categorias Mais Gastas */
    public List<CategoriaGastoDTO> getCategoriasMaisGastas(Usuario usuario, Long licitacaoId, int limit) {
        Licitacao licitacao = getLicitacaoUsuario(usuario, licitacaoId);
        return licitacao.getTransacoes().stream()
                .filter(t -> t.getTipoTransacao() == TipoTransacao.SAIDA)
                .collect(Collectors.groupingBy(t -> t.getCategoria().getNome(),
                        Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add)))
                .entrySet().stream()
                .map(entry -> new CategoriaGastoDTO(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> b.getTotalGasto().compareTo(a.getTotalGasto()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /** 🔹 Obtém o Fluxo de Caixa */
    public List<FluxoCaixaDTO> getFluxoCaixa(Usuario usuario, Long licitacaoId) {
        Licitacao licitacao = getLicitacaoUsuario(usuario, licitacaoId);
        return licitacao.getTransacoes().stream()
                .collect(Collectors.groupingBy(t -> t.getData().toLocalDate(),
                        Collectors.partitioningBy(t -> t.getTipoTransacao() == TipoTransacao.ENTRADA,
                                Collectors.reducing(BigDecimal.ZERO, Transacao::getValor, BigDecimal::add))))
                .entrySet().stream()
                .map(entry -> new FluxoCaixaDTO(entry.getKey(), entry.getValue().get(true), entry.getValue().get(false)))
                .sorted((a, b) -> a.getData().compareTo(b.getData()))
                .collect(Collectors.toList());
    }

    private BigDecimal calcularTotal(Licitacao licitacao, TipoTransacao tipo) {
        return licitacao.getTransacoes().stream()
                .filter(t -> t.getTipoTransacao() == tipo)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<FornecedorGastoDTO> getFornecedoresMaisPagos(Usuario usuario, Long licitacaoId) {
    	Licitacao licitacao = getLicitacaoUsuario(usuario, licitacaoId);
        return licitacaoRepository.findTopFornecedoresByLicitacaoId(licitacao.getId(), TipoTransacao.SAIDA);
    }

    private Licitacao validarPermissaoEObterLicitacao(Usuario usuario, Long licitacaoId) {
        boolean isAdmin = usuario.getPerfis().stream().anyMatch(perfil -> perfil.getNome().equals("ADMIN"));

        if (isAdmin) {
            return licitacaoRepository.findById(licitacaoId)
                    .orElseThrow(() -> new RuntimeException("Licitação não encontrada."));
        }

        boolean temPermissao = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getNome().equals("LICITACAO_" + licitacaoId));

        if (!temPermissao) {
            throw new RuntimeException("Usuário sem permissão para acessar essa licitação.");
        }

        return licitacaoRepository.findById(licitacaoId)
                .orElseThrow(() -> new RuntimeException("Licitação não encontrada."));
    }
    
    public SaldoAtualDTO getDashboardData(Long licitacaoId) {
        return licitacaoRepository.getResumoFinanceiro(licitacaoId);
    }

    public List<SaldoMensalDTO> getSaldoMensal(Long licitacaoId) {
        return licitacaoRepository.getSaldoMensal(licitacaoId);
    }

    public List<FluxoMensalDTO> getEntradasVsSaidas(Long licitacaoId) {
        return licitacaoRepository.getEntradasVsSaidas(licitacaoId);
    }

    public List<TipoGastoDTO> getTiposGasto(Long licitacaoId) {
        return licitacaoRepository.getTiposGasto(licitacaoId);
    }

    public List<MetodoPagamentoDTO> getMetodosPagamento(Long licitacaoId) {
        return licitacaoRepository.getMetodosPagamento(licitacaoId);
    }

    public List<ReceitaFonteDTO> getReceitaPorFonte(Long licitacaoId) {
        return licitacaoRepository.getReceitaPorFonte(licitacaoId);
    }

    public List<FornecedorGastoDTO> getFornecedoresMaisPagos(Long licitacaoId) {
        return licitacaoRepository.getFornecedoresMaisPagos(licitacaoId);
    }
    
    public List<Transacao> listarTransacoesPorTipo(Long licitacaoId, TipoTransacao tipo) {
        return transacaoRepository.findByLicitacaoIdAndTipo(licitacaoId, tipo);
    }
}

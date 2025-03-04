package com.mxm.licitacao.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.repositories.LicitacaoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicitacaoService {
	private final LicitacaoRepository licitacaoRepository;

	@Transactional
    public Licitacao criarLicitacao(Licitacao licitacao) {
        return licitacaoRepository.save(licitacao);
    }

    @Transactional
    public Licitacao editarLicitacao(Long id, Licitacao novaLicitacao) {
        Licitacao licitacao = licitacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Licitação não encontrada"));

        licitacao.setNome(novaLicitacao.getNome());
        licitacao.setDataInicio(novaLicitacao.getDataInicio());
        licitacao.setDataFim(novaLicitacao.getDataFim());
        licitacao.setSaldoAtual(novaLicitacao.getSaldoAtual());

        return licitacaoRepository.save(licitacao);
    }

    @Transactional
    public void excluirLicitacao(Long id) {
        licitacaoRepository.deleteById(id);
    }

    public List<Licitacao> listarLicitacoes() {
        return licitacaoRepository.findAll();
    }

    public Optional<Licitacao> buscarLicitacaoPorId(Long id) {
        return licitacaoRepository.findById(id);
    }

	public List<Licitacao> listarLicitacoesPorNomeNaLista(Set<String> nomesLicitacoesPermitidas) {
		return licitacaoRepository.findByNomeInIgnoreCase(nomesLicitacoesPermitidas);
	}
	
}

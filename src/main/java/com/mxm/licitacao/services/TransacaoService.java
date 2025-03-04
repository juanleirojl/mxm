package com.mxm.licitacao.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.entity.Licitacao;
import com.mxm.licitacao.entity.Transacao;
import com.mxm.licitacao.enums.TipoTransacao;
import com.mxm.licitacao.repositories.LicitacaoRepository;
import com.mxm.licitacao.repositories.TransacaoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final LicitacaoRepository licitacaoRepository;


    @Transactional
    public Transacao criarTransacao(Transacao transacao) {
        Licitacao licitacao = licitacaoRepository.findById(transacao.getLicitacao().getId())
            .orElseThrow(() -> new RuntimeException("Licitação não encontrada"));

        // Atualiza saldo antes de salvar a transação
        atualizarSaldoLicitacao(licitacao, transacao.getValor(), transacao.getTipoTransacao());

        return transacaoRepository.save(transacao);
    }

    @Transactional
    public Transacao editarTransacao(Long id, Transacao novaTransacao) {
        Transacao transacaoAntiga = transacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        Licitacao licitacao = transacaoAntiga.getLicitacao();

        // Reverter saldo da transação antiga
        reverterSaldoLicitacao(licitacao, transacaoAntiga.getValor(), transacaoAntiga.getTipoTransacao());

        // Aplicar novo valor
        atualizarSaldoLicitacao(licitacao, novaTransacao.getValor(), novaTransacao.getTipoTransacao());

        // Atualizar transação
        transacaoAntiga.setDescricao(novaTransacao.getDescricao());
        transacaoAntiga.setValor(novaTransacao.getValor());
        transacaoAntiga.setTipoTransacao(novaTransacao.getTipoTransacao());
        transacaoAntiga.setCategoria(novaTransacao.getCategoria());
        transacaoAntiga.setMetodoPagamento(novaTransacao.getMetodoPagamento());
        transacaoAntiga.setOrigemDestino(novaTransacao.getOrigemDestino());

        return transacaoRepository.save(transacaoAntiga);
    }

    @Transactional
    public void excluirTransacao(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        Licitacao licitacao = transacao.getLicitacao();

        // Reverter saldo antes de excluir
        reverterSaldoLicitacao(licitacao, transacao.getValor(), transacao.getTipoTransacao());

        transacaoRepository.delete(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarTransacaoPorId(Long id) {
        return transacaoRepository.findById(id);
    }

    private void atualizarSaldoLicitacao(Licitacao licitacao, BigDecimal valor, TipoTransacao tipo) {
        if (tipo == TipoTransacao.ENTRADA) {
            licitacao.setSaldoAtual(licitacao.getSaldoAtual().add(valor));
        } else if (tipo == TipoTransacao.SAIDA) {
            if (licitacao.getSaldoAtual().compareTo(valor) < 0) {
                throw new RuntimeException("Saldo insuficiente para esta transação!");
            }
            licitacao.setSaldoAtual(licitacao.getSaldoAtual().subtract(valor));
        }
        licitacaoRepository.save(licitacao);
    }

    private void reverterSaldoLicitacao(Licitacao licitacao, BigDecimal valor, TipoTransacao tipo) {
        if (tipo == TipoTransacao.ENTRADA) {
            licitacao.setSaldoAtual(licitacao.getSaldoAtual().subtract(valor));
        } else if (tipo == TipoTransacao.SAIDA) {
            licitacao.setSaldoAtual(licitacao.getSaldoAtual().add(valor));
        }
        licitacaoRepository.save(licitacao);
    }
}

package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.*;
import com.sincroniza.syncstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;
    
    @Autowired
    private EstoqueSaldoRepository estoqueSaldoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Listar movimentações com filtros opcionais
     */
    public List<MovimentacaoEstoque> listarMovimentacoes(Long unidadeId, Long produtoId, 
                                                          LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (unidadeId != null && produtoId != null) {
            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            Unidade unidade = unidadeRepository.findById(unidadeId).orElse(null);
            if (produto != null && unidade != null) {
                return movimentacaoRepository.findByProdutoAndUnidade(produto, unidade);
            }
        }
        
        if (dataInicio != null && dataFim != null) {
            return movimentacaoRepository.findByDataHoraBetween(dataInicio, dataFim);
        }
        
        return movimentacaoRepository.findAll();
    }
    
    /**
     * Registrar movimentação de estoque (entrada ou saída)
     */
    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoEstoque movimentacao) {
        // Validar produto
        Produto produto = produtoRepository.findById(movimentacao.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        movimentacao.setProduto(produto);
        
        // Validar unidade
        Unidade unidade = unidadeRepository.findById(movimentacao.getUnidade().getId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        movimentacao.setUnidade(unidade);
        
        // Validar usuário responsável
        Usuario usuario = usuarioRepository.findById(movimentacao.getUsuarioResponsavel().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        movimentacao.setUsuarioResponsavel(usuario);
        
        // Validar quantidade
        if (movimentacao.getQuantidade() == null || movimentacao.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }
        
        // Se for saída, verificar saldo disponível
        if (movimentacao.getTipo() == MovimentacaoTipo.SAIDA) {
            Optional<EstoqueSaldo> saldoOpt = estoqueSaldoRepository
                    .findByProdutoAndUnidade(produto, unidade);
            
            if (saldoOpt.isEmpty()) {
                throw new RuntimeException("Saldo insuficiente para saída");
            }
            
            EstoqueSaldo saldo = saldoOpt.get();
            if (saldo.getQuantidade().compareTo(movimentacao.getQuantidade()) < 0) {
                throw new RuntimeException("Saldo insuficiente. Disponível: " + saldo.getQuantidade());
            }
        }
        
        movimentacao.setDataHora(LocalDateTime.now());
        MovimentacaoEstoque saved = movimentacaoRepository.save(movimentacao);
        
        // Atualizar saldo
        atualizarSaldo(produto, unidade, movimentacao.getTipo(), movimentacao.getQuantidade());
        
        return saved;
    }
    
    /**
     * Atualizar o saldo de estoque após uma movimentação
     */
    private void atualizarSaldo(Produto produto, Unidade unidade, MovimentacaoTipo tipo, BigDecimal quantidade) {
        EstoqueSaldo saldo = estoqueSaldoRepository
                .findByProdutoAndUnidade(produto, unidade)
                .orElse(new EstoqueSaldo());
        
        if (saldo.getId() == null) {
            saldo.setProduto(produto);
            saldo.setUnidade(unidade);
            saldo.setQuantidade(BigDecimal.ZERO);
        }
        
        if (tipo == MovimentacaoTipo.ENTRADA) {
            saldo.adicionarQuantidade(quantidade);
        } else {
            saldo.removerQuantidade(quantidade);
        }
        
        estoqueSaldoRepository.save(saldo);
    }
    
    /**
     * Listar todo o saldo de estoque
     */
    public List<EstoqueSaldo> listarSaldo() {
        return estoqueSaldoRepository.findAll();
    }
    
    /**
     * Buscar saldo de um produto em uma unidade específica
     */
    public Optional<EstoqueSaldo> buscarSaldo(Long produtoId, Long unidadeId) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        Unidade unidade = unidadeRepository.findById(unidadeId).orElse(null);
        
        if (produto == null || unidade == null) {
            return Optional.empty();
        }
        
        return estoqueSaldoRepository.findByProdutoAndUnidade(produto, unidade);
    }
    
    /**
     * Listar alertas de produtos com estoque abaixo do mínimo
     */
    public List<EstoqueSaldo> listarAlertasEstoqueBaixo() {
        List<EstoqueSaldo> todosSaldos = estoqueSaldoRepository.findAll();
        
        return todosSaldos.stream()
                .filter(saldo -> {
                    BigDecimal estoqueMinimo = saldo.getProduto().getEstoqueMinimo();
                    return estoqueMinimo != null && saldo.getQuantidade().compareTo(estoqueMinimo) <= 0;
                })
                .toList();
    }
    
    /**
     * Verificar se um produto tem saldo suficiente em determinada unidade
     */
    public boolean temSaldoSuficiente(Long produtoId, Long unidadeId, BigDecimal quantidade) {
        Optional<EstoqueSaldo> saldo = buscarSaldo(produtoId, unidadeId);
        return saldo.isPresent() && saldo.get().getQuantidade().compareTo(quantidade) >= 0;
    }
}
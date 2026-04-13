package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.*;
import com.sincroniza.syncstock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    @Autowired
    private SetorRepository setorRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private EstoqueSaldoRepository estoqueSaldoRepository;
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
    
    public List<Pedido> listarPorUnidade(Long unidadeId) {
        Unidade unidade = unidadeRepository.findById(unidadeId).orElse(null);
        if (unidade != null) {
            return pedidoRepository.findByUnidadeSolicitante(unidade);
        }
        return List.of();
    }
    
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    @Transactional
    public Pedido criarPedido(Pedido pedido, List<ItemPedido> itens) {
        // Validar usuário
        Usuario usuario = usuarioRepository.findById(pedido.getUsuarioSolicitante().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        pedido.setUsuarioSolicitante(usuario);
        
        // Validar unidade
        Unidade unidade = unidadeRepository.findById(pedido.getUnidadeSolicitante().getId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        pedido.setUnidadeSolicitante(unidade);
        
        // Validar setor
        Setor setor = setorRepository.findById(pedido.getSetorSolicitante().getId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));
        pedido.setSetorSolicitante(setor);
        
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setDataCriacao(LocalDateTime.now());
        
        Pedido savedPedido = pedidoRepository.save(pedido);
        
        // Adicionar itens ao pedido
        for (ItemPedido item : itens) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            item.setProduto(produto);
            item.setPedido(savedPedido);
            savedPedido.getItens().add(item);
        }
        
        return pedidoRepository.save(savedPedido);
    }
    
    @Transactional
    public Pedido aprovarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser aprovados");
        }
        
        pedido.setStatus(StatusPedido.APROVADO);
        return pedidoRepository.save(pedido);
    }
    
    @Transactional
    public Pedido enviarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (pedido.getStatus() != StatusPedido.APROVADO) {
            throw new RuntimeException("Apenas pedidos aprovados podem ser enviados");
        }
        
        pedido.setStatus(StatusPedido.ENVIADO);
        return pedidoRepository.save(pedido);
    }
    
    @Transactional
    public Pedido receberPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (pedido.getStatus() != StatusPedido.ENVIADO) {
            throw new RuntimeException("Apenas pedidos enviados podem ser recebidos");
        }
        
        // Dar baixa no estoque para cada item
        for (ItemPedido item : pedido.getItens()) {
            EstoqueSaldo saldo = estoqueSaldoRepository
                    .findByProdutoAndUnidade(item.getProduto(), pedido.getUnidadeSolicitante())
                    .orElse(new EstoqueSaldo());
            
            if (saldo.getId() == null) {
                saldo.setProduto(item.getProduto());
                saldo.setUnidade(pedido.getUnidadeSolicitante());
                saldo.setQuantidade(item.getQuantidadeSolicitada());
            } else {
                saldo.adicionarQuantidade(item.getQuantidadeSolicitada());
            }
            
            item.setQuantidadeAtendida(item.getQuantidadeSolicitada());
            estoqueSaldoRepository.save(saldo);
        }
        
        pedido.setStatus(StatusPedido.RECEBIDO);
        return pedidoRepository.save(pedido);
    }
    
    @Transactional
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (pedido.getStatus() == StatusPedido.RECEBIDO) {
            throw new RuntimeException("Pedidos recebidos não podem ser cancelados");
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }
}
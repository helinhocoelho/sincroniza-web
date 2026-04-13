package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Produto;
import com.sincroniza.syncstock.model.Categoria;
import com.sincroniza.syncstock.model.Medida;
import com.sincroniza.syncstock.model.Fornecedor;
import com.sincroniza.syncstock.repository.ProdutoRepository;
import com.sincroniza.syncstock.repository.CategoriaRepository;
import com.sincroniza.syncstock.repository.MedidaRepository;
import com.sincroniza.syncstock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private MedidaRepository medidaRepository;
    
    @Autowired
    private FornecedorRepository fornecedorRepository;
    
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }
    
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }
    
    public Produto criarProduto(Produto produto) {
        // Validar e carregar relacionamentos
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }
        
        if (produto.getUnidadeMedida() != null && produto.getUnidadeMedida().getId() != null) {
            Medida medida = medidaRepository.findById(produto.getUnidadeMedida().getId())
                    .orElseThrow(() -> new RuntimeException("Unidade de medida não encontrada"));
            produto.setUnidadeMedida(medida);
        }
        
        if (produto.getFornecedor() != null && produto.getFornecedor().getId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
            produto.setFornecedor(fornecedor);
        }
        
        return produtoRepository.save(produto);
    }
    
    public Produto atualizarProduto(Long id, Produto produto) {
        Optional<Produto> existente = produtoRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }
        produto.setId(id);
        return criarProduto(produto);
    }
    
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
    
    public Produto desativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }
    
    public Produto ativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }
}
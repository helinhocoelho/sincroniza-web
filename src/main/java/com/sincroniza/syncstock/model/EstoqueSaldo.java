package com.sincroniza.syncstock.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "estoque_saldo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"produto_id", "unidade_id"})
})
public class EstoqueSaldo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
    
    private BigDecimal quantidade = BigDecimal.ZERO;
    
    public EstoqueSaldo() {}
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    
    public void adicionarQuantidade(BigDecimal valor) {
        this.quantidade = this.quantidade.add(valor);
    }
    
    public void removerQuantidade(BigDecimal valor) {
        this.quantidade = this.quantidade.subtract(valor);
    }
}
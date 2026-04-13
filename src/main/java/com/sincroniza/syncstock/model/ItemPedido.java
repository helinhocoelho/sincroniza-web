package com.sincroniza.syncstock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    private BigDecimal quantidadeSolicitada;
    
    private BigDecimal quantidadeAtendida = BigDecimal.ZERO;
    
    public ItemPedido() {}
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    
    public BigDecimal getQuantidadeSolicitada() { return quantidadeSolicitada; }
    public void setQuantidadeSolicitada(BigDecimal quantidadeSolicitada) { this.quantidadeSolicitada = quantidadeSolicitada; }
    
    public BigDecimal getQuantidadeAtendida() { return quantidadeAtendida; }
    public void setQuantidadeAtendida(BigDecimal quantidadeAtendida) { this.quantidadeAtendida = quantidadeAtendida; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
package com.sincroniza.syncstock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "setores")
public class Setor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    @JsonIgnoreProperties("setores")
    private Unidade unidade;
    
    public Setor() {}
    
    public Setor(String nome, Unidade unidade) {
        this.nome = nome;
        this.unidade = unidade;
    }
    
    public Setor(Long id) {
        this.id = id;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    
    @Override
    public String toString() {
        return nome;
    }
}
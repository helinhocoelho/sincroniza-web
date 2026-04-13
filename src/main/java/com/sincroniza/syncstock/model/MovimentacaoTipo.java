package com.sincroniza.syncstock.model;

public enum MovimentacaoTipo {
    ENTRADA("Entrada"),
    SAIDA("Saída");
    
    private final String nome;
    
    MovimentacaoTipo(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
}
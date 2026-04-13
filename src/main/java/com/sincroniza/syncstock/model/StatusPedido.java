package com.sincroniza.syncstock.model;

public enum StatusPedido {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    ENVIADO("Enviado"),
    RECEBIDO("Recebido"),
    CANCELADO("Cancelado");
    
    private final String nome;
    
    StatusPedido(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
}
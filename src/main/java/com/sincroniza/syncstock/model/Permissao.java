package com.sincroniza.syncstock.model;

public enum Permissao {
    ADMINISTRADOR("Administrador"),
    GERENTE("Gerente"),
    SOLICITANTE("Solicitante");
    
    private final String nome;
    
    Permissao(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
    
    public static Permissao fromNome(String nome) {
        for (Permissao p : Permissao.values()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Permissão inválida: " + nome);
    }
}
package com.sincroniza.syncstock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "unidades")
public class Unidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(unique = true, nullable = false)
    private String nome;
    
    @Email
    private String email;
    
    private String endereco;
    
    private String telefone;
    
    public Unidade() {}
    
    public Unidade(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public Unidade(String nome, String email, String endereco, String telefone) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    @Override
    public String toString() {
        return nome;
    }
}
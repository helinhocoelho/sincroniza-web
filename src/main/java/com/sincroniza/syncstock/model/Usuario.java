package com.sincroniza.syncstock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String nome;
    
    @NotBlank
    @Column(unique = true, nullable = false)
    private String login;
    
    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permissao permissao;
    
    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;
    
    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
    
    public Usuario() {}
    
    public Usuario(String nome, String login, String email, String senhaHash, 
                   Permissao permissao, Unidade unidade, Setor setor) {
        this.nome = nome;
        this.login = login;
        this.email = email;
        this.senhaHash = senhaHash;
        this.permissao = permissao;
        this.unidade = unidade;
        this.setor = setor;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    
    public Permissao getPermissao() { return permissao; }
    public void setPermissao(Permissao permissao) { this.permissao = permissao; }
    
    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }
    
    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", login='" + login + '\'' + 
               ", permissao=" + (permissao != null ? permissao.getNome() : "N/A") + '}';
    }
}
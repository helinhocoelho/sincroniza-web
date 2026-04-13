package com.sincroniza.syncstock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/usuarios")
    public String usuarios() {
        return "usuarios";
    }
    
    @GetMapping("/unidades")
    public String unidades() {
        return "unidades";
    }
    
    @GetMapping("/setores")
    public String setores() {
        return "setores";
    }
    
    @GetMapping("/produtos")
    public String produtos() {
        return "produtos";
    }
    
    @GetMapping("/fornecedores")
    public String fornecedores() {
        return "fornecedores";
    }
    
    @GetMapping("/pedidos")
    public String pedidos() {
        return "pedidos";
    }
    
    @GetMapping("/estoque")
    public String estoque() {
        return "estoque";
    }
    
    @GetMapping("/relatorios")
    public String relatorios() {
        return "relatorios";
    }
    
    // Redirecionar raiz para login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}
package com.sincroniza.syncstock.service;

import org.springframework.stereotype.Service;

@Service
public class ValidacaoService {
    
    /**
     * Valida a força da senha conforme critérios:
     * - Mínimo de 6 caracteres
     * - Pelo menos 1 número
     * - Pelo menos 1 letra maiúscula
     * - Pelo menos 1 caractere especial
     */
    public boolean validarForcaSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            return false;
        }
        
        boolean temNumero = senha.matches(".*\\d.*");
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temEspecial = senha.matches(".*[!@#$%&*].*");
        
        return temNumero && temMaiuscula && temEspecial;
    }
    
    /**
     * Valida formato de email
     */
    public boolean validarEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
    
    /**
     * Valida se o login é válido (sem espaços, mínimo 3 caracteres)
     */
    public boolean validarLogin(String login) {
        if (login == null || login.length() < 3) {
            return false;
        }
        return !login.contains(" ");
    }
}
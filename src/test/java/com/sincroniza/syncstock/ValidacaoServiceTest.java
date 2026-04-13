package com.sincroniza.syncstock;

import com.sincroniza.syncstock.service.ValidacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ValidacaoServiceTest {
    
    private ValidacaoService validacaoService;
    
    @BeforeEach
    void setUp() {
        validacaoService = new ValidacaoService();
    }
    
    // ========== TESTES PARA VALIDAÇÃO DE SENHA ==========
    
    @Test
    void testValidarForcaSenha_Valida() {
        assertTrue(validacaoService.validarForcaSenha("Senha123!"));
        assertTrue(validacaoService.validarForcaSenha("Admin@2024"));
        assertTrue(validacaoService.validarForcaSenha("Teste#99"));
    }
    
    @Test
    void testValidarForcaSenha_MenosDe6Caracteres() {
        assertFalse(validacaoService.validarForcaSenha("Ab1!"));
        assertFalse(validacaoService.validarForcaSenha("123"));
        assertFalse(validacaoService.validarForcaSenha(""));
    }
    
    @Test
    void testValidarForcaSenha_SemNumero() {
        assertFalse(validacaoService.validarForcaSenha("SenhaForte!"));
        assertFalse(validacaoService.validarForcaSenha("Abcdef!"));
    }
    
    @Test
    void testValidarForcaSenha_SemMaiuscula() {
        assertFalse(validacaoService.validarForcaSenha("senha123!"));
        assertFalse(validacaoService.validarForcaSenha("abc123!"));
    }
    
    @Test
    void testValidarForcaSenha_SemEspecial() {
        assertFalse(validacaoService.validarForcaSenha("Senha123"));
        assertFalse(validacaoService.validarForcaSenha("Admin2024"));
    }
    
    @Test
    void testValidarForcaSenha_Null() {
        assertFalse(validacaoService.validarForcaSenha(null));
    }
    
    // ========== TESTES PARA VALIDAÇÃO DE EMAIL ==========
    
    @Test
    void testValidarEmail_Valido() {
        assertTrue(validacaoService.validarEmail("admin@sincroniza.com"));
        assertTrue(validacaoService.validarEmail("usuario123@escola.br"));
        assertTrue(validacaoService.validarEmail("teste+email@gmail.com"));
    }
    
    @Test
    void testValidarEmail_Invalido() {
        assertFalse(validacaoService.validarEmail("email_invalido"));
        assertFalse(validacaoService.validarEmail("sem_arroba.com"));
        assertFalse(validacaoService.validarEmail("@dominio.com"));
        assertFalse(validacaoService.validarEmail(""));
        assertFalse(validacaoService.validarEmail(null));
    }
    
    // ========== TESTES PARA VALIDAÇÃO DE LOGIN ==========
    
    @Test
    void testValidarLogin_Valido() {
        assertTrue(validacaoService.validarLogin("admin"));
        assertTrue(validacaoService.validarLogin("usuario123"));
        assertTrue(validacaoService.validarLogin("abc"));
    }
    
    @Test
    void testValidarLogin_Invalido() {
        assertFalse(validacaoService.validarLogin("ab"));
        assertFalse(validacaoService.validarLogin(""));
        assertFalse(validacaoService.validarLogin("usuario com espaco"));
        assertFalse(validacaoService.validarLogin(null));
    }
}
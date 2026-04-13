package com.sincroniza.syncstock;

import com.sincroniza.syncstock.model.Permissao;
import com.sincroniza.syncstock.model.Usuario;
import com.sincroniza.syncstock.service.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SyncstockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SyncstockApplication.class, args);
        
        // === TESTES MANUAIS ===
        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        
        System.out.println("\n=== INICIANDO TESTES DO SINCRONIZA WEB ===\n");
        
        // Teste 1: Criar usuário
        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("Administrador Sistema");
            novoUsuario.setLogin("admin");
            novoUsuario.setEmail("admin@sincroniza.com");
            novoUsuario.setSenhaHash("$2a$10$hashdasenha");
            novoUsuario.setPermissao(Permissao.ADMINISTRADOR);
            novoUsuario.setUnidade(null);
            novoUsuario.setSetor(null);
            
            Usuario salvo = usuarioService.criarUsuario(novoUsuario);
            System.out.println("✅ Teste 1 OK: Usuário criado com ID: " + salvo.getId());
        } catch (Exception e) {
            System.out.println("⚠️ Teste 1: " + e.getMessage());
        }
        
        // Teste 2: Buscar por login
        usuarioService.buscarPorLogin("admin").ifPresentOrElse(
            u -> System.out.println("✅ Teste 2 OK: Usuário encontrado: " + u.getLogin()),
            () -> System.out.println("❌ Teste 2: Usuário não encontrado")
        );
        
        System.out.println("\n=== TESTES FINALIZADOS ===\n");
    }
}
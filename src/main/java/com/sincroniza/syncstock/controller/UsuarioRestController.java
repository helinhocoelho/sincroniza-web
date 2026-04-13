package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.Usuario;
import com.sincroniza.syncstock.model.Permissao;
import com.sincroniza.syncstock.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = (List<Usuario>) usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }
    
    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    // Criar novo usuário
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            // Validar força da senha (será feito no front-end também)
            if (usuario.getSenhaHash() == null || usuario.getSenhaHash().length() < 6) {
                return ResponseEntity.badRequest().body("Senha deve ter no mínimo 6 caracteres");
            }
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioExistente = usuarioService.buscarPorId(id);
            if (usuarioExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Usuario u = usuarioExistente.get();
            u.setNome(usuario.getNome());
            u.setLogin(usuario.getLogin());
            u.setEmail(usuario.getEmail());
            u.setPermissao(usuario.getPermissao());
            u.setUnidade(usuario.getUnidade());
            u.setSetor(usuario.getSetor());
            
            // Só atualiza senha se foi enviada
            if (usuario.getSenhaHash() != null && !usuario.getSenhaHash().isEmpty()) {
                u.setSenhaHash(usuario.getSenhaHash());
            }
            
            Usuario atualizado = usuarioService.criarUsuario(u);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar usuário");
        }
    }
    
    // Listar permissões disponíveis (para o front-end)
    @GetMapping("/permissoes")
    public ResponseEntity<Permissao[]> listarPermissoes() {
        return ResponseEntity.ok(Permissao.values());
    }
}
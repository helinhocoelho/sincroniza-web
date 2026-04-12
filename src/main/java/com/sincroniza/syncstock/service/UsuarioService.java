package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Usuario;
import com.sincroniza.syncstock.model.Permissao;
import com.sincroniza.syncstock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Criar usuário com validações (RF001)
    public Usuario criarUsuario(Usuario usuario) {
        // Validar unicidade do login
        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            throw new RuntimeException("Login já existe!");
        }
        
        // Validar unicidade do email
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }
        
        // Validar força da senha (será implementado depois com PasswordHasher)
        
        return usuarioRepository.save(usuario);
    }
    
    // Autenticar usuário (RF010)
    public Optional<Usuario> autenticar(String login, String senhaHash) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        
        if (usuario.isPresent() && usuario.get().getSenhaHash().equals(senhaHash)) {
            return usuario;
        }
        
        return Optional.empty();
    }
    
    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    // Buscar por login
    public Optional<Usuario> buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
    
    // Listar todos (apenas para ADMIN)
    public Iterable<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    // Deletar usuário (RF001 - restrito a ADMIN)
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
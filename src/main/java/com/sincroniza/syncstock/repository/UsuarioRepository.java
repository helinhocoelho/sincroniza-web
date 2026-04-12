package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar por login (para autenticação)
    Optional<Usuario> findByLogin(String login);
    
    // Buscar por email
    Optional<Usuario> findByEmail(String email);
    
    // Verificar se login já existe
    boolean existsByLogin(String login);
    
    // Verificar se email já existe
    boolean existsByEmail(String email);
}
package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Categoria;
import com.sincroniza.syncstock.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Categoria criarCategoria(Categoria categoria) {
        if (categoriaRepository.findByNome(categoria.getNome()).isPresent()) {
            throw new RuntimeException("Categoria já existe!");
        }
        return categoriaRepository.save(categoria);
    }
    
    public Categoria atualizarCategoria(Long id, Categoria categoria) {
        Optional<Categoria> existente = categoriaRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada");
        }
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }
    
    public void deletarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
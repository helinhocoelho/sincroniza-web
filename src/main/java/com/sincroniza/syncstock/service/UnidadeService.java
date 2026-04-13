package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Unidade;
import com.sincroniza.syncstock.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    public List<Unidade> listarTodos() {
        return unidadeRepository.findAll();
    }
    
    public Optional<Unidade> buscarPorId(Long id) {
        return unidadeRepository.findById(id);
    }
    
    public Unidade criarUnidade(Unidade unidade) {
        if (unidadeRepository.existsByNome(unidade.getNome())) {
            throw new RuntimeException("Unidade já existe!");
        }
        return unidadeRepository.save(unidade);
    }
    
    public Unidade atualizarUnidade(Long id, Unidade unidade) {
        Optional<Unidade> existente = unidadeRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Unidade não encontrada");
        }
        unidade.setId(id);
        return unidadeRepository.save(unidade);
    }
    
    public void deletarUnidade(Long id) {
        unidadeRepository.deleteById(id);
    }
}
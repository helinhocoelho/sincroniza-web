package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Setor;
import com.sincroniza.syncstock.model.Unidade;
import com.sincroniza.syncstock.repository.SetorRepository;
import com.sincroniza.syncstock.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SetorService {
    
    @Autowired
    private SetorRepository setorRepository;
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    public List<Setor> listarTodos() {
        return setorRepository.findAll();
    }
    
    public List<Setor> listarPorUnidade(Long unidadeId) {
        return setorRepository.findByUnidadeId(unidadeId);
    }
    
    public Optional<Setor> buscarPorId(Long id) {
        return setorRepository.findById(id);
    }
    
    public Setor criarSetor(Setor setor) {
        if (setor.getUnidade() != null && setor.getUnidade().getId() != null) {
            Unidade unidade = unidadeRepository.findById(setor.getUnidade().getId())
                    .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
            setor.setUnidade(unidade);
        }
        return setorRepository.save(setor);
    }
    
    public Setor atualizarSetor(Long id, Setor setor) {
        Optional<Setor> existente = setorRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Setor não encontrado");
        }
        setor.setId(id);
        return setorRepository.save(setor);
    }
    
    public void deletarSetor(Long id) {
        setorRepository.deleteById(id);
    }
}
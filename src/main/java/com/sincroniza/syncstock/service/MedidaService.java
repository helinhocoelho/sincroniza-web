package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Medida;
import com.sincroniza.syncstock.repository.MedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedidaService {
    
    @Autowired
    private MedidaRepository medidaRepository;
    
    public List<Medida> listarTodos() {
        return medidaRepository.findAll();
    }
    
    public Optional<Medida> buscarPorId(Long id) {
        return medidaRepository.findById(id);
    }
    
    public Medida criarMedida(Medida medida) {
        if (medidaRepository.findByNome(medida.getNome()).isPresent()) {
            throw new RuntimeException("Medida já existe!");
        }
        return medidaRepository.save(medida);
    }
    
    public Medida atualizarMedida(Long id, Medida medida) {
        Optional<Medida> existente = medidaRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Medida não encontrada");
        }
        medida.setId(id);
        return medidaRepository.save(medida);
    }
    
    public void deletarMedida(Long id) {
        medidaRepository.deleteById(id);
    }
}
package com.sincroniza.syncstock.service;

import com.sincroniza.syncstock.model.Fornecedor;
import com.sincroniza.syncstock.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    
    @Autowired
    private FornecedorRepository fornecedorRepository;
    
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }
    
    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }
    
    public Fornecedor criarFornecedor(Fornecedor fornecedor) {
        if (fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()) {
            if (fornecedorRepository.findByCnpj(fornecedor.getCnpj()).isPresent()) {
                throw new RuntimeException("CNPJ já cadastrado!");
            }
        }
        return fornecedorRepository.save(fornecedor);
    }
    
    public Fornecedor atualizarFornecedor(Long id, Fornecedor fornecedor) {
        Optional<Fornecedor> existente = fornecedorRepository.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Fornecedor não encontrado");
        }
        fornecedor.setId(id);
        return fornecedorRepository.save(fornecedor);
    }
    
    public void deletarFornecedor(Long id) {
        fornecedorRepository.deleteById(id);
    }
}
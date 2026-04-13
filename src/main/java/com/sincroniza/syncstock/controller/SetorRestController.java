package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.Setor;
import com.sincroniza.syncstock.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/setores")
@CrossOrigin(origins = "*")
public class SetorRestController {
    
    @Autowired
    private SetorService setorService;
    
    @GetMapping
    public List<Setor> listar() {
        return setorService.listarTodos();
    }
    
    @GetMapping("/unidade/{unidadeId}")
    public List<Setor> listarPorUnidade(@PathVariable Long unidadeId) {
        return setorService.listarPorUnidade(unidadeId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Setor> buscar(@PathVariable Long id) {
        return setorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Setor setor) {
        try {
            return ResponseEntity.ok(setorService.criarSetor(setor));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Setor setor) {
        try {
            return ResponseEntity.ok(setorService.atualizarSetor(id, setor));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            setorService.deletarSetor(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar setor");
        }
    }
}
package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.Unidade;
import com.sincroniza.syncstock.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@CrossOrigin(origins = "*")
public class UnidadeRestController {
    
    @Autowired
    private UnidadeService unidadeService;
    
    @GetMapping
    public List<Unidade> listar() {
        return unidadeService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Unidade> buscar(@PathVariable Long id) {
        return unidadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Unidade unidade) {
        try {
            return ResponseEntity.ok(unidadeService.criarUnidade(unidade));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Unidade unidade) {
        try {
            return ResponseEntity.ok(unidadeService.atualizarUnidade(id, unidade));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            unidadeService.deletarUnidade(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar unidade");
        }
    }
}
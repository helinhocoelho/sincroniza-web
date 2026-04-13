package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.Medida;
import com.sincroniza.syncstock.service.MedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medidas")
@CrossOrigin(origins = "*")
public class MedidaRestController {
    
    @Autowired
    private MedidaService medidaService;
    
    @GetMapping
    public List<Medida> listar() {
        return medidaService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Medida> buscar(@PathVariable Long id) {
        return medidaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Medida medida) {
        try {
            return ResponseEntity.ok(medidaService.criarMedida(medida));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Medida medida) {
        try {
            return ResponseEntity.ok(medidaService.atualizarMedida(id, medida));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            medidaService.deletarMedida(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar medida");
        }
    }
}
package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.MovimentacaoEstoque;
import com.sincroniza.syncstock.model.EstoqueSaldo;
import com.sincroniza.syncstock.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@CrossOrigin(origins = "*")
public class EstoqueRestController {
    
    @Autowired
    private EstoqueService estoqueService;
    
    @GetMapping("/movimentacoes")
    public List<MovimentacaoEstoque> listarMovimentacoes(
            @RequestParam(required = false) Long unidadeId,
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) LocalDateTime dataInicio,
            @RequestParam(required = false) LocalDateTime dataFim) {
        return estoqueService.listarMovimentacoes(unidadeId, produtoId, dataInicio, dataFim);
    }
    
    @PostMapping("/movimentacoes")
    public ResponseEntity<?> registrarMovimentacao(@RequestBody MovimentacaoEstoque movimentacao) {
        try {
            return ResponseEntity.ok(estoqueService.registrarMovimentacao(movimentacao));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/saldo")
    public List<EstoqueSaldo> listarSaldo() {
        return estoqueService.listarSaldo();
    }
    
    @GetMapping("/saldo/produto/{produtoId}/unidade/{unidadeId}")
    public ResponseEntity<EstoqueSaldo> buscarSaldo(@PathVariable Long produtoId, @PathVariable Long unidadeId) {
        return estoqueService.buscarSaldo(produtoId, unidadeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/alertas/estoque-baixo")
    public List<EstoqueSaldo> listarAlertasEstoqueBaixo() {
        return estoqueService.listarAlertasEstoqueBaixo();
    }
}
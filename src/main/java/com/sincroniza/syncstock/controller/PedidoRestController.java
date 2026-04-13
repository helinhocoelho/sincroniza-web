package com.sincroniza.syncstock.controller;

import com.sincroniza.syncstock.model.Pedido;
import com.sincroniza.syncstock.model.ItemPedido;
import com.sincroniza.syncstock.model.Produto;
import com.sincroniza.syncstock.model.Setor;
import com.sincroniza.syncstock.model.StatusPedido;
import com.sincroniza.syncstock.model.Unidade;
import com.sincroniza.syncstock.model.Usuario;
import com.sincroniza.syncstock.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoRestController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/status/{status}")
    public List<Pedido> listarPorStatus(@PathVariable String status) {
        return pedidoService.listarPorStatus(StatusPedido.valueOf(status));
    }

    @GetMapping("/unidade/{unidadeId}")
    public List<Pedido> listarPorUnidade(@PathVariable Long unidadeId) {
        return pedidoService.listarPorUnidade(unidadeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> request) {
        try {
            // Extrair dados do pedido
            Map<String, Object> unidadeMap = (Map<String, Object>) request.get("unidadeSolicitante");
            Map<String, Object> setorMap = (Map<String, Object>) request.get("setorSolicitante");
            Map<String, Object> usuarioMap = (Map<String, Object>) request.get("usuarioSolicitante");
            List<Map<String, Object>> itensList = (List<Map<String, Object>>) request.get("itens");

            // Validar dados obrigatórios
            if (unidadeMap == null || setorMap == null || usuarioMap == null || itensList == null) {
                return ResponseEntity.badRequest().body("Dados incompletos: unidade, setor, usuario e itens são obrigatórios");
            }

            // Criar objeto Pedido
            Pedido pedido = new Pedido();

            Unidade unidade = new Unidade();
            unidade.setId(Long.valueOf(unidadeMap.get("id").toString()));
            pedido.setUnidadeSolicitante(unidade);

            Setor setor = new Setor();
            setor.setId(Long.valueOf(setorMap.get("id").toString()));
            pedido.setSetorSolicitante(setor);

            Usuario usuario = new Usuario();
            usuario.setId(Long.valueOf(usuarioMap.get("id").toString()));
            pedido.setUsuarioSolicitante(usuario);

            // Criar lista de itens
            List<ItemPedido> itens = new ArrayList<>();
            for (Map<String, Object> itemMap : itensList) {
                ItemPedido item = new ItemPedido();

                Map<String, Object> produtoMap = (Map<String, Object>) itemMap.get("produto");
                if (produtoMap == null || produtoMap.get("id") == null) {
                    return ResponseEntity.badRequest().body("Item sem produto informado");
                }

                Produto produto = new Produto();
                produto.setId(Long.valueOf(produtoMap.get("id").toString()));
                item.setProduto(produto);

                Object qtdObj = itemMap.get("quantidadeSolicitada");
                if (qtdObj == null) {
                    return ResponseEntity.badRequest().body("Item sem quantidade informada");
                }

                item.setQuantidadeSolicitada(new BigDecimal(qtdObj.toString()));
                itens.add(item);
            }

            if (itens.isEmpty()) {
                return ResponseEntity.badRequest().body("Pedido deve ter pelo menos um item");
            }

            Pedido savedPedido = pedidoService.criarPedido(pedido, itens);
            return ResponseEntity.ok(savedPedido);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Erro no formato dos IDs: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao criar pedido: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.aprovarPedido(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<?> enviar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.enviarPedido(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/receber")
    public ResponseEntity<?> receber(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.receberPedido(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.cancelarPedido(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status-options")
    public ResponseEntity<StatusPedido[]> listarStatus() {
        return ResponseEntity.ok(StatusPedido.values());
    }
}

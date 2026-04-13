package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.MovimentacaoEstoque;
import com.sincroniza.syncstock.model.Produto;
import com.sincroniza.syncstock.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findByProdutoAndUnidade(Produto produto, Unidade unidade);
    List<MovimentacaoEstoque> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
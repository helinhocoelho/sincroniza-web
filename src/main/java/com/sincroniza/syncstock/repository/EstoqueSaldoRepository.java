package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.EstoqueSaldo;
import com.sincroniza.syncstock.model.Produto;
import com.sincroniza.syncstock.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstoqueSaldoRepository extends JpaRepository<EstoqueSaldo, Long> {
    Optional<EstoqueSaldo> findByProdutoAndUnidade(Produto produto, Unidade unidade);
}
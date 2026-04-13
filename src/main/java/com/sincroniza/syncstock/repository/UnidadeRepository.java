package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    Optional<Unidade> findByNome(String nome);
    boolean existsByNome(String nome);
}
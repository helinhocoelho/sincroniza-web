package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.Medida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Long> {
    Optional<Medida> findByNome(String nome);
}
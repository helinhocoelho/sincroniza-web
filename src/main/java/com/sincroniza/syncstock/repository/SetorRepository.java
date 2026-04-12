package com.sincroniza.syncstock.repository;

import com.sincroniza.syncstock.model.Setor;
import com.sincroniza.syncstock.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    
    List<Setor> findByUnidade(Unidade unidade);
    
    List<Setor> findByUnidadeId(Long unidadeId);
}
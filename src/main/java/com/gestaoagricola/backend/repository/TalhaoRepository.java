package com.gestaoagricola.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestaoagricola.backend.entity.Talhao;
import java.util.List;

public interface TalhaoRepository extends JpaRepository<Talhao, Long> {

    List<Talhao> findByPropriedadeId(Long propriedadeId);

    boolean existsByPropriedadeId(Long propriedadeId);

}

package com.gestaoagricola.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestaoagricola.backend.entity.CulturaAgricola;

public interface CulturaAgricolaRepository
        extends JpaRepository<CulturaAgricola, Long> {

    boolean existsByNome(String nome);
}

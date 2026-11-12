
package com.gestaoagricola.backend.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestaoagricola.backend.entity.Plantio;
import com.gestaoagricola.backend.entity.StatusPlantio;

public interface PlantioRepository extends JpaRepository<Plantio, Long> {

    boolean existsByTalhaoIdAndStatus(
            Long talhaoId,
            StatusPlantio status);

    boolean existsByTalhaoIdAndStatusAndIdNot(
            Long talhaoId,
            StatusPlantio status,
            Long id);

    boolean existsByTalhaoId(Long talhaoId);

    boolean existsByCulturaId(Long culturaId);

    @Query("SELECT MAX(p.areaPlantada) FROM Plantio p WHERE p.talhao.id = :talhaoId")
    BigDecimal buscarMaiorAreaPlantada(@Param("talhaoId") Long talhaoId);
}

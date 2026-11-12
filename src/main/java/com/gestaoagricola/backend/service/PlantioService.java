
package com.gestaoagricola.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gestaoagricola.backend.entity.CulturaAgricola;
import com.gestaoagricola.backend.entity.Plantio;
import com.gestaoagricola.backend.entity.StatusPlantio;
import com.gestaoagricola.backend.entity.Talhao;
import com.gestaoagricola.backend.repository.CulturaAgricolaRepository;
import com.gestaoagricola.backend.repository.PlantioRepository;
import com.gestaoagricola.backend.repository.TalhaoRepository;

@Service
public class PlantioService {

    private final PlantioRepository repository;
    private final TalhaoRepository talhaoRepository;
    private final CulturaAgricolaRepository culturaRepository;

    public PlantioService(
            PlantioRepository repository,
            TalhaoRepository talhaoRepository,
            CulturaAgricolaRepository culturaRepository) {

        this.repository = repository;
        this.talhaoRepository = talhaoRepository;
        this.culturaRepository = culturaRepository;
    }

    public List<Plantio> listarTodos() {
        return repository.findAll();
    }

    public Plantio buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Plantio não encontrado."));
    }

    public Plantio cadastrar(Plantio plantio) {

        if (plantio == null) {
            throw new IllegalArgumentException(
                    "Os dados do plantio são obrigatórios.");
        }

        if (plantio.getTalhao() == null ||
                plantio.getTalhao().getId() == null) {

            throw new IllegalArgumentException(
                    "É necessário informar um talhão.");
        }

        Talhao talhao = talhaoRepository.findById(
                plantio.getTalhao().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Talhão não encontrado."));

        plantio.setTalhao(talhao);

        if (plantio.getCultura() == null ||
                plantio.getCultura().getId() == null) {

            throw new IllegalArgumentException(
                    "É necessário informar uma cultura agrícola.");
        }

        CulturaAgricola cultura = culturaRepository.findById(
                plantio.getCultura().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cultura agrícola não encontrada."));

        plantio.setCultura(cultura);

        validarDados(plantio, null);

        return repository.save(plantio);
    }

    public Plantio atualizar(Long id, Plantio dados) {

        Plantio existente = buscarPorId(id);

        if (dados == null) {
            throw new IllegalArgumentException(
                    "Os dados do plantio são obrigatórios.");
        }

        if (dados.getTalhao() != null &&
                dados.getTalhao().getId() != null &&
                !dados.getTalhao().getId().equals(existente.getTalhao().getId())) {

            throw new IllegalArgumentException(
                    "Não é permitido alterar o talhão do plantio.");
        }

        if (dados.getCultura() == null ||
                dados.getCultura().getId() == null) {

            throw new IllegalArgumentException(
                    "É necessário informar uma cultura agrícola.");
        }

        CulturaAgricola cultura = culturaRepository.findById(
                dados.getCultura().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cultura agrícola não encontrada."));

        Plantio atualizado = new Plantio();

        atualizado.setTalhao(existente.getTalhao());
        atualizado.setCultura(cultura);
        atualizado.setAreaPlantada(dados.getAreaPlantada());
        atualizado.setDataPrevista(dados.getDataPrevista());
        atualizado.setDataRealizada(dados.getDataRealizada());
        atualizado.setStatus(dados.getStatus());

        validarDados(atualizado, id);

        existente.setCultura(atualizado.getCultura());
        existente.setAreaPlantada(atualizado.getAreaPlantada());
        existente.setDataPrevista(atualizado.getDataPrevista());
        existente.setDataRealizada(atualizado.getDataRealizada());
        existente.setStatus(atualizado.getStatus());

        return repository.save(existente);
    }

    private void validarDados(Plantio plantio, Long idIgnorado) {

        if (plantio.getAreaPlantada() == null ||
                plantio.getAreaPlantada().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "A área plantada deve ser maior que zero.");
        }

        if (plantio.getAreaPlantada()
                .compareTo(plantio.getTalhao().getArea()) > 0) {

            throw new IllegalArgumentException(
                    "A área plantada não pode ultrapassar a área do talhão.");
        }

        if (plantio.getDataPrevista() == null) {
            throw new IllegalArgumentException(
                    "A data prevista do plantio é obrigatória.");
        }

        if (plantio.getStatus() == null) {
            throw new IllegalArgumentException(
                    "O status do plantio é obrigatório.");
        }

        if (plantio.getStatus() == StatusPlantio.REALIZADO &&
                plantio.getDataRealizada() == null) {

            throw new IllegalArgumentException(
                    "A data realizada é obrigatória para plantios realizados.");
        }

        if ((plantio.getStatus() == StatusPlantio.PLANEJADO ||
                plantio.getStatus() == StatusPlantio.CANCELADO) &&
                plantio.getDataRealizada() != null) {

            throw new IllegalArgumentException(
                    "Plantios planejados ou cancelados não podem ter data realizada.");
        }

        if (plantio.getStatus() == StatusPlantio.PLANEJADO) {

            boolean existeOutro;

            if (idIgnorado == null) {
                existeOutro = repository.existsByTalhaoIdAndStatus(
                        plantio.getTalhao().getId(),
                        StatusPlantio.PLANEJADO);
            } else {
                existeOutro = repository.existsByTalhaoIdAndStatusAndIdNot(
                        plantio.getTalhao().getId(),
                        StatusPlantio.PLANEJADO,
                        idIgnorado);
            }

            if (existeOutro) {
                throw new IllegalArgumentException(
                        "Já existe um plantio planejado neste talhão.");
            }
        }
    }

    public void deletar(Long id) {
        Plantio plantio = buscarPorId(id);
        repository.delete(plantio);
    }
}

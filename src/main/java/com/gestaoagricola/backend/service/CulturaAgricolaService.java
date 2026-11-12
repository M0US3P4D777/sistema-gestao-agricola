
package com.gestaoagricola.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gestaoagricola.backend.entity.CulturaAgricola;
import com.gestaoagricola.backend.repository.CulturaAgricolaRepository;
import com.gestaoagricola.backend.repository.PlantioRepository;

@Service
public class CulturaAgricolaService {

    private final CulturaAgricolaRepository culturaAgricolaRepository;
    private final PlantioRepository plantioRepository;

    public CulturaAgricolaService(
            CulturaAgricolaRepository culturaAgricolaRepository,
            PlantioRepository plantioRepository) {

        this.culturaAgricolaRepository = culturaAgricolaRepository;
        this.plantioRepository = plantioRepository;
    }

    public List<CulturaAgricola> listarTodas() {
        return culturaAgricolaRepository.findAll();
    }

    public CulturaAgricola cadastrar(CulturaAgricola cultura) {

        if (cultura == null ||
                cultura.getNome() == null ||
                cultura.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "O nome da cultura é obrigatório.");
        }

        if (culturaAgricolaRepository.existsByNome(cultura.getNome())) {
            throw new IllegalArgumentException(
                    "Já existe uma cultura cadastrada com esse nome.");
        }

        return culturaAgricolaRepository.save(cultura);
    }

    public CulturaAgricola buscarPorId(Long id) {
        return culturaAgricolaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Cultura agrícola não encontrada."));
    }

    public CulturaAgricola atualizar(Long id, CulturaAgricola dados) {

        CulturaAgricola culturaExistente = buscarPorId(id);

        if (dados == null ||
                dados.getNome() == null ||
                dados.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "O nome da cultura é obrigatório.");
        }

        if (!culturaExistente.getNome().equals(dados.getNome())
                && culturaAgricolaRepository.existsByNome(dados.getNome())) {

            throw new IllegalArgumentException(
                    "Já existe uma cultura cadastrada com esse nome.");
        }

        culturaExistente.setNome(dados.getNome());

        return culturaAgricolaRepository.save(culturaExistente);
    }

    public void excluir(Long id) {

        CulturaAgricola cultura = buscarPorId(id);

        if (plantioRepository.existsByCulturaId(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir uma cultura que possui plantios vinculados.");
        }

        culturaAgricolaRepository.delete(cultura);
    }
}

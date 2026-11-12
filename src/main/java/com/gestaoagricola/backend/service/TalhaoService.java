
package com.gestaoagricola.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gestaoagricola.backend.entity.PropriedadeRural;
import com.gestaoagricola.backend.entity.Talhao;
import com.gestaoagricola.backend.repository.PlantioRepository;
import com.gestaoagricola.backend.repository.PropriedadeRuralRepository;
import com.gestaoagricola.backend.repository.TalhaoRepository;

@Service
public class TalhaoService {

    private final TalhaoRepository repository;
    private final PropriedadeRuralRepository propriedadeRepository;
    private final PlantioRepository plantioRepository;

    public TalhaoService(
            TalhaoRepository repository,
            PropriedadeRuralRepository propriedadeRepository,
            PlantioRepository plantioRepository) {

        this.repository = repository;
        this.propriedadeRepository = propriedadeRepository;
        this.plantioRepository = plantioRepository;
    }

    public List<Talhao> listarTodos() {
        return repository.findAll();
    }

    public Talhao cadastrar(Talhao talhao) {

        if (talhao == null) {
            throw new IllegalArgumentException(
                    "Os dados do talhão são obrigatórios.");
        }

        if (talhao.getNome() == null ||
                talhao.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do talhão é obrigatório.");
        }

        if (talhao.getArea() == null ||
                talhao.getArea().signum() <= 0) {
            throw new IllegalArgumentException(
                    "A área do talhão deve ser maior que zero.");
        }

        if (talhao.getPropriedade() == null ||
                talhao.getPropriedade().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar uma propriedade rural.");
        }

        Long propriedadeId = talhao.getPropriedade().getId();

        PropriedadeRural propriedade = propriedadeRepository
                .findById(propriedadeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Propriedade rural não encontrada."));

        talhao.setPropriedade(propriedade);

        BigDecimal areaOcupada = BigDecimal.ZERO;

        List<Talhao> talhoesExistentes =
                repository.findByPropriedadeId(propriedadeId);

        for (Talhao existente : talhoesExistentes) {
            areaOcupada = areaOcupada.add(existente.getArea());
        }

        BigDecimal novaAreaTotal =
                areaOcupada.add(talhao.getArea());

        if (novaAreaTotal.compareTo(propriedade.getAreaTotal()) > 0) {
            throw new IllegalArgumentException(
                    "A área dos talhões ultrapassa a área total da propriedade.");
        }

        return repository.save(talhao);
    }

    public Talhao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Talhão não encontrado."));
    }

    public Talhao atualizar(Long id, Talhao dados) {

        Talhao talhaoExistente = buscarPorId(id);

        if (dados == null ||
                dados.getNome() == null ||
                dados.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do talhão é obrigatório.");
        }

        if (dados.getArea() == null ||
                dados.getArea().signum() <= 0) {
            throw new IllegalArgumentException(
                    "A área do talhão deve ser maior que zero.");
        }

        Long propriedadeId =
                talhaoExistente.getPropriedade().getId();

        PropriedadeRural propriedade = propriedadeRepository
                .findById(propriedadeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Propriedade rural não encontrada."));

        BigDecimal areaOcupada = BigDecimal.ZERO;

        List<Talhao> talhoesExistentes =
                repository.findByPropriedadeId(propriedadeId);

        for (Talhao existente : talhoesExistentes) {
            areaOcupada = areaOcupada.add(existente.getArea());
        }

        BigDecimal areaSemTalhaoAtual =
                areaOcupada.subtract(talhaoExistente.getArea());

        BigDecimal novaAreaTotal =
                areaSemTalhaoAtual.add(dados.getArea());

        if (novaAreaTotal.compareTo(propriedade.getAreaTotal()) > 0) {
            throw new IllegalArgumentException(
                    "A área dos talhões ultrapassa a área total da propriedade.");
        }

        BigDecimal maiorAreaPlantada =
                plantioRepository.buscarMaiorAreaPlantada(id);

        if (maiorAreaPlantada != null &&
                dados.getArea().compareTo(maiorAreaPlantada) < 0) {
            throw new IllegalArgumentException(
                    "A área do talhão não pode ser menor que a área de um plantio vinculado.");
        }

        talhaoExistente.setNome(dados.getNome());
        talhaoExistente.setArea(dados.getArea());

        return repository.save(talhaoExistente);
    }

    public void excluir(Long id) {

        Talhao talhao = buscarPorId(id);

        if (plantioRepository.existsByTalhaoId(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir um talhão que possui plantios vinculados.");
        }

        repository.delete(talhao);
    }
}

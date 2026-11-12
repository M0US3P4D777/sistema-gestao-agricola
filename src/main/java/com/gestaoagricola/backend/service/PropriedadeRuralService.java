
package com.gestaoagricola.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gestaoagricola.backend.entity.PropriedadeRural;
import com.gestaoagricola.backend.entity.Talhao;
import com.gestaoagricola.backend.repository.PropriedadeRuralRepository;
import com.gestaoagricola.backend.repository.TalhaoRepository;

@Service
public class PropriedadeRuralService {

    private final PropriedadeRuralRepository repository;
    private final TalhaoRepository talhaoRepository;

    public PropriedadeRuralService(
            PropriedadeRuralRepository repository,
            TalhaoRepository talhaoRepository) {

        this.repository = repository;
        this.talhaoRepository = talhaoRepository;
    }

    public List<PropriedadeRural> listarTodas() {
        return repository.findAll();
    }

    public PropriedadeRural cadastrar(PropriedadeRural propriedade) {

        if (propriedade == null) {
            throw new IllegalArgumentException(
                    "Os dados da propriedade são obrigatórios.");
        }

        validarDados(propriedade);

        return repository.save(propriedade);
    }

    public PropriedadeRural buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Propriedade não encontrada: " + id));
    }

    public PropriedadeRural atualizar(Long id, PropriedadeRural dados) {

        PropriedadeRural propriedade = buscarPorId(id);

        if (dados == null) {
            throw new IllegalArgumentException(
                    "Os dados da propriedade são obrigatórios.");
        }

        validarDados(dados);

        List<Talhao> talhoes = talhaoRepository.findByPropriedadeId(id);

        BigDecimal areaOcupada = BigDecimal.ZERO;

        for (Talhao talhao : talhoes) {
            areaOcupada = areaOcupada.add(talhao.getArea());
        }

        if (dados.getAreaTotal().compareTo(areaOcupada) < 0) {
            throw new IllegalArgumentException(
                    "A área total não pode ser menor que a soma das áreas dos talhões.");
        }

        propriedade.setNome(dados.getNome());
        propriedade.setAreaTotal(dados.getAreaTotal());
        propriedade.setMunicipio(dados.getMunicipio());

        return repository.save(propriedade);
    }

    private void validarDados(PropriedadeRural propriedade) {

        if (propriedade.getNome() == null ||
                propriedade.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da propriedade é obrigatório.");
        }

        if (propriedade.getMunicipio() == null ||
                propriedade.getMunicipio().isBlank()) {
            throw new IllegalArgumentException(
                    "O município é obrigatório.");
        }

        if (propriedade.getAreaTotal() == null ||
                propriedade.getAreaTotal().signum() <= 0) {
            throw new IllegalArgumentException(
                    "A área total deve ser maior que zero.");
        }
    }

    public void excluir(Long id) {

        PropriedadeRural propriedade = buscarPorId(id);

        if (talhaoRepository.existsByPropriedadeId(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir uma propriedade que possui talhões vinculados.");
        }

        repository.delete(propriedade);
    }
}

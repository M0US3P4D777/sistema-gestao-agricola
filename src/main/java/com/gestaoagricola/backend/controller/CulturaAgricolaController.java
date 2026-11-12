package com.gestaoagricola.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.gestaoagricola.backend.entity.CulturaAgricola;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gestaoagricola.backend.service.CulturaAgricolaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/culturas")
public class CulturaAgricolaController {
    private final CulturaAgricolaService service;

    public CulturaAgricolaController(CulturaAgricolaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CulturaAgricola> listarTodas() {
        return service.listarTodas();
    }

    @PostMapping
    public CulturaAgricola cadastrar(@RequestBody CulturaAgricola cultura) {
        return service.cadastrar(cultura);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErro(IllegalArgumentException erro) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro.getMessage());
    }

    @GetMapping("/{id}")
    public CulturaAgricola buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(NoSuchElementException erro) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro.getMessage());
    }

    @PutMapping("/{id}")
    public CulturaAgricola atualizar(
            @PathVariable Long id,
            @RequestBody CulturaAgricola dados) {

        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
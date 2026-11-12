
package com.gestaoagricola.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaoagricola.backend.entity.PropriedadeRural;
import com.gestaoagricola.backend.service.PropriedadeRuralService;

@RestController
@RequestMapping("/propriedades")
public class PropriedadeRuralController {

    private final PropriedadeRuralService service;

    public PropriedadeRuralController(PropriedadeRuralService service) {
        this.service = service;
    }

    @GetMapping
    public List<PropriedadeRural> listarTodas() {
        return service.listarTodas();
    }

    @PostMapping
    public ResponseEntity<PropriedadeRural> cadastrar(
            @RequestBody PropriedadeRural propriedade) {

        PropriedadeRural novaPropriedade = service.cadastrar(propriedade);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaPropriedade);
    }

    @GetMapping("/{id}")
    public PropriedadeRural buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PropriedadeRural atualizar(
            @PathVariable Long id,
            @RequestBody PropriedadeRural dados) {

        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErroValidacao(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(
            NoSuchElementException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}

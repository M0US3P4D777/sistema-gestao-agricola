
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

import com.gestaoagricola.backend.entity.Plantio;
import com.gestaoagricola.backend.service.PlantioService;

@RestController
@RequestMapping("/plantios")
public class PlantioController {

    private final PlantioService service;

    public PlantioController(PlantioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Plantio> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Plantio buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Plantio> cadastrar(@RequestBody Plantio plantio) {
        Plantio novoPlantio = service.cadastrar(plantio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlantio);
    }

    @PutMapping("/{id}")
    public Plantio atualizar(
            @PathVariable Long id,
            @RequestBody Plantio plantio) {

        return service.atualizar(id, plantio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErroValidacao(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(
            NoSuchElementException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}

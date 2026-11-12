
package com.gestaoagricola.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.gestaoagricola.backend.entity.Talhao;
import com.gestaoagricola.backend.service.TalhaoService;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService service;

    public TalhaoController(TalhaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Talhao> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public Talhao cadastrar(@RequestBody Talhao talhao) {
        return service.cadastrar(talhao);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErro(
            IllegalArgumentException erro) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro.getMessage());
    }

    @GetMapping("/{id}")
    public Talhao buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarNaoEncontrado(NoSuchElementException erro) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro.getMessage());
    }

    @PutMapping("/{id}")
    public Talhao atualizar(
            @PathVariable Long id,
            @RequestBody Talhao dados) {

        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

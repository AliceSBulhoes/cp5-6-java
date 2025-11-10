package br.fiap.checkpoiny.reagentes_api.reagentes_api.controller;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.FabricanteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.service.FabricanteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável por gerenciar os recursos de Fabricante.
 * 
 * Fornece endpoints CRUD conforme a arquitetura RESTful.
 * 
 * Endpoints:
 *  - GET /api/fabricantes → Lista todos os fabricantes
 *  - GET /api/fabricantes/{id} → Busca fabricante por ID
 *  - POST /api/fabricantes → Cria novo fabricante
 *  - PUT /api/fabricantes/{id} → Atualiza fabricante existente
 *  - DELETE /api/fabricantes/{id} → Remove fabricante
 */
@RestController
@RequestMapping("/api/fabricantes")
public class FabricanteController {

    private final FabricanteService service;

    /**
     * Injeta o serviço de Fabricante via construtor.
     */
    public FabricanteController(FabricanteService service) {
        this.service = service;
    }

    /**
     * Retorna a lista de todos os fabricantes.
     */
    @GetMapping
    public ResponseEntity<List<FabricanteDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * Retorna um fabricante específico pelo seu ID.
     * Caso o ID não exista, retorna HTTP 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FabricanteDTO> buscar(@PathVariable UUID id) {
        var dto = service.buscar(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    /**
     * Cria um novo fabricante.
     * Retorna HTTP 201 (Created) com o objeto criado.
     */
    @PostMapping
    public ResponseEntity<FabricanteDTO> criar(@RequestBody FabricanteDTO d) {
        var criado = service.criar(d);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * Atualiza os dados de um fabricante existente.
     * Caso o ID não exista, retorna HTTP 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FabricanteDTO> atualizar(@PathVariable UUID id, @RequestBody FabricanteDTO d) {
        var atualizado = service.atualizar(id, d);
        if (atualizado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um fabricante pelo ID.
     * Retorna HTTP 204 se removido ou 404 se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        if (!service.remover(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

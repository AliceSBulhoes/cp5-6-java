package br.fiap.checkpoiny.reagentes_api.reagentes_api.controller;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.ReagenteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.service.ReagenteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller para operações CRUD de Reagente.
 * Cada método retorna o HTTP Status adequado conforme o enunciado.
 */
@RestController
@RequestMapping("/api/reagentes")
public class ReagenteController {

    private final ReagenteService service;

    public ReagenteController(ReagenteService service) {
        this.service = service;
    }

    /**
     * Lista todos os reagentes.
     * @return 200 OK com a lista
     */
    @GetMapping
    public ResponseEntity<List<ReagenteDTO>> listar() {
        List<ReagenteDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca reagente por ID.
     * @param id UUID do reagente
     * @return 200 OK com o reagente ou 404 Not Found se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReagenteDTO> buscarPorId(@PathVariable UUID id) {
        ReagenteDTO dto = service.buscarPorId(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Cria um novo reagente.
     * @param dto payload com dados
     * @return 201 Created com o objeto criado
     */
    @PostMapping
    public ResponseEntity<ReagenteDTO> criar(@RequestBody ReagenteDTO dto) {
        ReagenteDTO criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * Atualiza um reagente existente.
     * @param id UUID do reagente
     * @param dto payload com novos dados
     * @return 200 OK com o objeto atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReagenteDTO> atualizar(@PathVariable UUID id, @RequestBody ReagenteDTO dto) {
        ReagenteDTO atualizado = service.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um reagente.
     * @param id UUID do reagente
     * @return 204 No Content em caso de sucesso, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        boolean removed = service.remover(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}

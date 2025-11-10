package br.fiap.checkpoiny.reagentes_api.reagentes_api.controller;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.MovimentacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para Movimentações de Estoque.
 * 
 * Endpoints:
 *  - GET /api/movimentacoes → lista todas
 *  - GET /api/movimentacoes/{id} → busca uma movimentação
 *  - POST /api/movimentacoes/reagente/{reagenteId} → cria nova movimentação
 *  - PUT /api/movimentacoes/{id} → atualiza movimentação
 *  - DELETE /api/movimentacoes/{id} → remove movimentação
 */
@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> buscar(@PathVariable UUID id) {
        var dto = service.buscar(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/reagente/{reagenteId}")
    public ResponseEntity<MovimentacaoDTO> criar(
            @PathVariable UUID reagenteId,
            @RequestBody MovimentacaoDTO dto) {
        var criado = service.criar(reagenteId, dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> atualizar(
            @PathVariable UUID id,
            @RequestBody MovimentacaoDTO dto) {
        var atualizado = service.atualizar(id, dto);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        if (!service.remover(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

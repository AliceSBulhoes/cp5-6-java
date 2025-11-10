package br.fiap.checkpoiny.reagentes_api.reagentes_api.controller;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.LocalizacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.service.LocalizacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável pelas Localizações de Estoque.
 */
@RestController
@RequestMapping("/api/localizacoes")
public class LocalizacaoController {

    private final LocalizacaoService service;

    public LocalizacaoController(LocalizacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LocalizacaoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizacaoDTO> buscar(@PathVariable UUID id) {
        var dto = service.buscar(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<LocalizacaoDTO> criar(@RequestBody LocalizacaoDTO dto) {
        var criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalizacaoDTO> atualizar(@PathVariable UUID id, @RequestBody LocalizacaoDTO dto) {
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

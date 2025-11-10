package br.fiap.checkpoiny.reagentes_api.reagentes_api.service;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.FabricanteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper.FabricanteMapper;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Fabricante;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.FabricanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável pelas regras de negócio de Fabricante.
 */
@Service
@Transactional
public class FabricanteService {

    private final FabricanteRepository repo;

    public FabricanteService(FabricanteRepository repo) {
        this.repo = repo;
    }

    /** Lista todos os fabricantes cadastrados. */
    public List<FabricanteDTO> listar() {
        return repo.findAll().stream()
                .map(FabricanteMapper::toDTO)
                .collect(Collectors.toList());
    }

    /** Busca um fabricante pelo seu ID. */
    public FabricanteDTO buscar(UUID id) {
        return repo.findById(id)
                .map(FabricanteMapper::toDTO)
                .orElse(null);
    }

    /** Cria um novo fabricante. */
    public FabricanteDTO criar(FabricanteDTO dto) {
        Fabricante entidade = FabricanteMapper.toEntity(dto);
        return FabricanteMapper.toDTO(repo.save(entidade));
    }

    /** Atualiza os dados de um fabricante existente. */
    public FabricanteDTO atualizar(UUID id, FabricanteDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setNomeOficial(dto.nomeOficial());
            existing.setNomeFantasia(dto.nomeFantasia());
            existing.setCnpj(dto.cnpj());
            existing.setPaisOrigem(dto.paisOrigem());
            return FabricanteMapper.toDTO(repo.save(existing));
        }).orElse(null);
    }

    /** Remove um fabricante pelo ID. */
    public boolean remover(UUID id) {
        return repo.findById(id).map(f -> {
            repo.delete(f);
            return true;
        }).orElse(false);
    }
}

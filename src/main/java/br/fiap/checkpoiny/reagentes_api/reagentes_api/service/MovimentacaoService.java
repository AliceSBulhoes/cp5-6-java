package br.fiap.checkpoiny.reagentes_api.reagentes_api.service;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.MovimentacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper.MovimentacaoMapper;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.MovimentacaoEstoque;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.MovimentacaoRepository;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.ReagenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço que gerencia as movimentações de estoque dos reagentes.
 */
@Service
@Transactional
public class MovimentacaoService {

    private final MovimentacaoRepository repo;
    private final ReagenteRepository reagenteRepo;

    public MovimentacaoService(MovimentacaoRepository repo, ReagenteRepository reagenteRepo) {
        this.repo = repo;
        this.reagenteRepo = reagenteRepo;
    }

    /** Lista todas as movimentações realizadas. */
    public List<MovimentacaoDTO> listar() {
        return repo.findAll().stream()
                .map(MovimentacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /** Busca uma movimentação pelo ID. */
    public MovimentacaoDTO buscar(UUID id) {
        return repo.findById(id)
                .map(MovimentacaoMapper::toDTO)
                .orElse(null);
    }

    /** Cria uma movimentação vinculada a um reagente. */
    public MovimentacaoDTO criar(UUID reagenteId, MovimentacaoDTO dto) {
        var reagente = reagenteRepo.findById(reagenteId)
                .orElseThrow(() -> new IllegalArgumentException("Reagente não encontrado"));

        MovimentacaoEstoque m = MovimentacaoMapper.toEntity(dto);
        m.setReagente(reagente);

        // Atualiza a quantidade do reagente
        if (m.getQuantidadeMovimentada() != null) {
            int atual = reagente.getQuantidadeEmEstoque() == null ? 0 : reagente.getQuantidadeEmEstoque();
            reagente.setQuantidadeEmEstoque(atual + m.getQuantidadeMovimentada());
            reagenteRepo.save(reagente);
        }

        return MovimentacaoMapper.toDTO(repo.save(m));
    }

    /** Atualiza os dados de uma movimentação. */
    public MovimentacaoDTO atualizar(UUID id, MovimentacaoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setTipo(dto.tipo());
            existing.setQuantidadeMovimentada(dto.quantidadeMovimentada());
            existing.setObservacao(dto.observacao());
            return MovimentacaoMapper.toDTO(repo.save(existing));
        }).orElse(null);
    }

    /** Remove uma movimentação pelo ID. */
    public boolean remover(UUID id) {
        return repo.findById(id).map(m -> {
            repo.delete(m);
            return true;
        }).orElse(false);
    }
}

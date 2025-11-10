package br.fiap.checkpoiny.reagentes_api.reagentes_api.service;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.LocalizacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper.LocalizacaoMapper;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.LocalizacaoEstoque;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.LocalizacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas regras de negócio da Localização de Estoque.
 */
@Service
@Transactional
public class LocalizacaoService {

    private final LocalizacaoRepository repo;

    public LocalizacaoService(LocalizacaoRepository repo) {
        this.repo = repo;
    }

    /** Lista todas as localizações cadastradas. */
    public List<LocalizacaoDTO> listar() {
        return repo.findAll().stream()
                .map(LocalizacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /** Busca uma localização pelo ID. */
    public LocalizacaoDTO buscar(UUID id) {
        return repo.findById(id)
                .map(LocalizacaoMapper::toDTO)
                .orElse(null);
    }

    /** Cria uma nova localização. */
    public LocalizacaoDTO criar(LocalizacaoDTO dto) {
        LocalizacaoEstoque entidade = LocalizacaoMapper.toEntity(dto);
        return LocalizacaoMapper.toDTO(repo.save(entidade));
    }

    /** Atualiza os dados de uma localização existente. */
    public LocalizacaoDTO atualizar(UUID id, LocalizacaoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setCodigoLocal(dto.codigoLocal());
            existing.setDescricao(dto.descricao());
            existing.setTipo(dto.tipo());
            existing.setFaixaTemperaturaNominal(dto.faixaTemperaturaNominal());
            existing.setSetor(dto.setor());
            return LocalizacaoMapper.toDTO(repo.save(existing));
        }).orElse(null);
    }

    /** Remove uma localização pelo ID. */
    public boolean remover(UUID id) {
        return repo.findById(id).map(l -> {
            repo.delete(l);
            return true;
        }).orElse(false);
    }
}

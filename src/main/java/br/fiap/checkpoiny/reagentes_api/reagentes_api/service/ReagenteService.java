package br.fiap.checkpoiny.reagentes_api.reagentes_api.service;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.ReagenteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper.ReagenteMapper;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Reagente;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.ReagenteRepository;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.FabricanteRepository;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.repository.LocalizacaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço que contém regras de negócio para Reagente.
 * Exemplo de responsabilidades:
 *  - validações simples
 *  - operações transacionais
 *  - conversão entre DTO/entidade
 */
@Service
@Transactional
public class ReagenteService {

    private final ReagenteRepository repository;
    private final FabricanteRepository fabricanteRepository;
    private final LocalizacaoRepository localizacaoRepository;

    public ReagenteService(ReagenteRepository repository,
                          FabricanteRepository fabricanteRepository,
                          LocalizacaoRepository localizacaoRepository) {
        this.repository = repository;
        this.fabricanteRepository = fabricanteRepository;
        this.localizacaoRepository = localizacaoRepository;
    }

    /**
     * Lista todos os reagentes como DTOs.
     * @return lista de ReagenteDTO
     */
    public List<ReagenteDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ReagenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca por id.
     * @param id UUID do reagente
     * @return ReagenteDTO ou null se não encontrado
     */
    public ReagenteDTO buscarPorId(UUID id) {
        return repository.findById(id).map(ReagenteMapper::toDTO).orElse(null);
    }

    /**
     * Cria um novo reagente.
     * @param dto DTO com os dados a criar
     * @return ReagenteDTO criado
     */
    public ReagenteDTO criar(ReagenteDTO dto) {
        // Regras de negócio simples: quantidade >= 0
        if (dto.quantidadeEmEstoque() != null && dto.quantidadeEmEstoque() < 0) {
            throw new IllegalArgumentException("quantidadeEmEstoque deve ser >= 0");
        }
        Reagente r = ReagenteMapper.toEntity(dto);
        // Se vier referência a Fabricante por id, busque a entidade gerenciada
        if (dto.fabricante() != null && dto.fabricante().id() != null) {
            var fabId = dto.fabricante().id();
            var fab = fabricanteRepository.findById(fabId)
                    .orElseThrow(() -> new IllegalArgumentException("Fabricante não encontrado: " + fabId));
            r.setFabricante(fab);
        } else if (r.getFabricante() != null) {
            // se vier dados completos do fabricante (sem id), persista primeiro
            var novoFab = fabricanteRepository.save(r.getFabricante());
            r.setFabricante(novoFab);
        }

        // Mesmo para Localizacao: se referencia por id, busque; senão, crie
        if (dto.localizacao() != null && dto.localizacao().id() != null) {
            var locId = dto.localizacao().id();
            var loc = localizacaoRepository.findById(locId)
                    .orElseThrow(() -> new IllegalArgumentException("Localizacao não encontrada: " + locId));
            r.setLocalizacao(loc);
        } else if (r.getLocalizacao() != null) {
            var novaLoc = localizacaoRepository.save(r.getLocalizacao());
            r.setLocalizacao(novaLoc);
        }

        Reagente salvo = repository.save(r);
        return ReagenteMapper.toDTO(salvo);
    }

    /**
     * Atualiza um reagente existente.
     * @param id UUID do reagente
     * @param dto DTO com novos dados
     * @return ReagenteDTO atualizado ou null se não encontrado
     */
    public ReagenteDTO atualizar(UUID id, ReagenteDTO dto) {
        return repository.findById(id).map(existing -> {
            // Atualiza campos permitidos
            existing.setNome(dto.nome());
            existing.setCodigoSku(dto.codigoSku());
            existing.setLote(dto.lote());
            existing.setDataValidade(dto.dataValidade());
            existing.setDataRecebimento(dto.dataRecebimento());
            existing.setQuantidadeEmEstoque(dto.quantidadeEmEstoque());
            existing.setStatus(dto.status());
            // fabricantes e localizacoes são substituídos
            // Atualiza Fabricante: se veio id, busque; se veio dados, crie/atualize
            var candidateFab = ReagenteMapper.toEntity(dto).getFabricante();
            if (dto.fabricante() != null && dto.fabricante().id() != null) {
                var fab = fabricanteRepository.findById(dto.fabricante().id())
                        .orElseThrow(() -> new IllegalArgumentException("Fabricante não encontrado: " + dto.fabricante().id()));
                existing.setFabricante(fab);
            } else if (candidateFab != null) {
                var savedFab = fabricanteRepository.save(candidateFab);
                existing.setFabricante(savedFab);
            }

            var candidateLoc = ReagenteMapper.toEntity(dto).getLocalizacao();
            if (dto.localizacao() != null && dto.localizacao().id() != null) {
                var loc = localizacaoRepository.findById(dto.localizacao().id())
                        .orElseThrow(() -> new IllegalArgumentException("Localizacao não encontrada: " + dto.localizacao().id()));
                existing.setLocalizacao(loc);
            } else if (candidateLoc != null) {
                var savedLoc = localizacaoRepository.save(candidateLoc);
                existing.setLocalizacao(savedLoc);
            }
            Reagente salvo = repository.save(existing);
            return ReagenteMapper.toDTO(salvo);
        }).orElse(null);
    }

    /**
     * Remove um reagente.
     * @param id UUID do reagente
     * @return true se removido, false se não encontrado
     */
    public boolean remover(UUID id) {
        return repository.findById(id).map(r -> {
            repository.delete(r);
            return true;
        }).orElse(false);
    }
}

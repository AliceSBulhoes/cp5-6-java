package br.fiap.checkpoiny.reagentes_api.reagentes_api.repository;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.MovimentacaoEstoque;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository para MovimentacaoEstoque.
 */
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, UUID> {
}

package br.fiap.checkpoiny.reagentes_api.reagentes_api.repository;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.LocalizacaoEstoque;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository para LocalizacaoEstoque.
 */
public interface LocalizacaoRepository extends JpaRepository<LocalizacaoEstoque, UUID> {
}

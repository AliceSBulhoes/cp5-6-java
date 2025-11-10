package br.fiap.checkpoiny.reagentes_api.reagentes_api.repository;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Fabricante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository para Fabricante.
 */
public interface FabricanteRepository extends JpaRepository<Fabricante, UUID> {
}

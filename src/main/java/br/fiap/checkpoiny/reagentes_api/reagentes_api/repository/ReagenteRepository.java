package br.fiap.checkpoiny.reagentes_api.reagentes_api.repository;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Reagente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository Spring Data JPA para Reagente.
 */
public interface ReagenteRepository extends JpaRepository<Reagente, UUID> {
}

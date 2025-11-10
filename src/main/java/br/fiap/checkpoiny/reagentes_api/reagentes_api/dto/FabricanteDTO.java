package br.fiap.checkpoiny.reagentes_api.reagentes_api.dto;

import java.util.UUID;

/**
 * DTO record para Fabricante.
 * Java record fornece uma forma concisa de representar dados imutáveis.
 */
public record FabricanteDTO(
        UUID id,
        String nomeOficial,
        String nomeFantasia,
        String cnpj,
        String paisOrigem
) {}

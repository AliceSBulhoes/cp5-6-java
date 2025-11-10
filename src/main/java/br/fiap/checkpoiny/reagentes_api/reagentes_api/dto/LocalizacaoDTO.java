package br.fiap.checkpoiny.reagentes_api.reagentes_api.dto;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.TipoLocalizacaoEstoque;

import java.util.UUID;

/**
 * DTO record para LocalizacaoEstoque.
 */
public record LocalizacaoDTO(
        UUID id,
        String codigoLocal,
        String descricao,
        TipoLocalizacaoEstoque tipo,
        String faixaTemperaturaNominal,
        String setor
) {}

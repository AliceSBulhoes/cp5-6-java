package br.fiap.checkpoiny.reagentes_api.reagentes_api.dto;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.TipoMovimentacao;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO record para MovimentacaoEstoque.
 */
public record MovimentacaoDTO(
        UUID id,
        LocalDateTime dataHoraMovimentacao,
        TipoMovimentacao tipo,
        Integer quantidadeMovimentada,
        String observacao
) {}

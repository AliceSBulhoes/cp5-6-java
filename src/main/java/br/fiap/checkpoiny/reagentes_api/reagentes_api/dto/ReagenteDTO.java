package br.fiap.checkpoiny.reagentes_api.reagentes_api.dto;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.StatusReagente;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO record para Reagente. Contém informações principais e referências aos DTOs
 * de Fabricante e Localizacao.
 */
public record ReagenteDTO(
        UUID id,
        String nome,
        String codigoSku,
        String lote,
        LocalDate dataValidade,
        LocalDate dataRecebimento,
        Integer quantidadeEmEstoque,
        StatusReagente status,
        FabricanteDTO fabricante,
        LocalizacaoDTO localizacao,
        List<MovimentacaoDTO> movimentacoes
) {}

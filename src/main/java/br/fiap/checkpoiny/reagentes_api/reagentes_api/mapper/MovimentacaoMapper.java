package br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.MovimentacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.MovimentacaoEstoque;

/**
 * Mapper para MovimentacaoEstoque <-> MovimentacaoDTO
 */
public class MovimentacaoMapper {

    public static MovimentacaoDTO toDTO(MovimentacaoEstoque m) {
        if (m == null) return null;
        return new MovimentacaoDTO(
                m.getId(),
                m.getDataHoraMovimentacao(),
                m.getTipo(),
                m.getQuantidadeMovimentada(),
                m.getObservacao()
        );
    }

    public static MovimentacaoEstoque toEntity(MovimentacaoDTO dto) {
        if (dto == null) return null;
        MovimentacaoEstoque m = new MovimentacaoEstoque();
        if (dto.id() != null) m.setId(dto.id());
        m.setDataHoraMovimentacao(dto.dataHoraMovimentacao());
        m.setTipo(dto.tipo());
        m.setQuantidadeMovimentada(dto.quantidadeMovimentada());
        m.setObservacao(dto.observacao());
        return m;
    }
}

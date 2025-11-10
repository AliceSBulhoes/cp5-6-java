package br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.LocalizacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.LocalizacaoEstoque;

/**
 * Mapper manual para LocalizacaoEstoque <-> LocalizacaoDTO.
 */
public class LocalizacaoMapper {

    public static LocalizacaoDTO toDTO(LocalizacaoEstoque l) {
        if (l == null) return null;
        return new LocalizacaoDTO(
                l.getId(),
                l.getCodigoLocal(),
                l.getDescricao(),
                l.getTipo(),
                l.getFaixaTemperaturaNominal(),
                l.getSetor()
        );
    }

    public static LocalizacaoEstoque toEntity(LocalizacaoDTO dto) {
        if (dto == null) return null;
        LocalizacaoEstoque l = new LocalizacaoEstoque();
        if (dto.id() != null) l.setId(dto.id());
        l.setCodigoLocal(dto.codigoLocal());
        l.setDescricao(dto.descricao());
        l.setTipo(dto.tipo());
        l.setFaixaTemperaturaNominal(dto.faixaTemperaturaNominal());
        l.setSetor(dto.setor());
        return l;
    }
}

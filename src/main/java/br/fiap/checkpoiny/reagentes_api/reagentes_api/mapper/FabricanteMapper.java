package br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.FabricanteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Fabricante;

/**
 * Mapper manual entre Fabricante e FabricanteDTO.
 * Implementado de forma explícita para fins didáticos.
 */
public class FabricanteMapper {

    public static FabricanteDTO toDTO(Fabricante f) {
        if (f == null) return null;
        return new FabricanteDTO(
                f.getId(),
                f.getNomeOficial(),
                f.getNomeFantasia(),
                f.getCnpj(),
                f.getPaisOrigem()
        );
    }

    public static Fabricante toEntity(FabricanteDTO dto) {
        if (dto == null) return null;
        Fabricante f = new Fabricante();
        if (dto.id() != null) f.setId(dto.id());
        f.setNomeOficial(dto.nomeOficial());
        f.setNomeFantasia(dto.nomeFantasia());
        f.setCnpj(dto.cnpj());
        f.setPaisOrigem(dto.paisOrigem());
        return f;
    }
}

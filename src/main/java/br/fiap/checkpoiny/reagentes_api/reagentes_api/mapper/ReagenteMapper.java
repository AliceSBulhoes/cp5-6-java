package br.fiap.checkpoiny.reagentes_api.reagentes_api.mapper;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.ReagenteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.FabricanteDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.LocalizacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.dto.MovimentacaoDTO;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Reagente;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.Fabricante;
import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity.LocalizacaoEstoque;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper manual para Reagente <-> ReagenteDTO.
 */
public class ReagenteMapper {

    public static ReagenteDTO toDTO(Reagente r) {
        if (r == null) return null;
        FabricanteDTO fab = FabricanteMapper.toDTO(r.getFabricante());
        LocalizacaoDTO loc = LocalizacaoMapper.toDTO(r.getLocalizacao());
        List<MovimentacaoDTO> movs = r.getMovimentacoes().stream()
                .map(MovimentacaoMapper::toDTO)
                .collect(Collectors.toList());
        return new ReagenteDTO(
                r.getId(),
                r.getNome(),
                r.getCodigoSku(),
                r.getLote(),
                r.getDataValidade(),
                r.getDataRecebimento(),
                r.getQuantidadeEmEstoque(),
                r.getStatus(),
                fab,
                loc,
                movs
        );
    }

    public static Reagente toEntity(ReagenteDTO dto) {
        if (dto == null) return null;
        Reagente r = new Reagente();
        if (dto.id() != null) r.setId(dto.id());
        r.setNome(dto.nome());
        r.setCodigoSku(dto.codigoSku());
        r.setLote(dto.lote());
        r.setDataValidade(dto.dataValidade());
        r.setDataRecebimento(dto.dataRecebimento());
        r.setQuantidadeEmEstoque(dto.quantidadeEmEstoque());
        r.setStatus(dto.status());
        // Map fabricante/localizacao with helper mappers
        Fabricante f = FabricanteMapper.toEntity(dto.fabricante());
        LocalizacaoEstoque l = LocalizacaoMapper.toEntity(dto.localizacao());
        r.setFabricante(f);
        r.setLocalizacao(l);
        // Movimentacoes serão adicionadas via serviço quando necessário
        return r;
    }
}

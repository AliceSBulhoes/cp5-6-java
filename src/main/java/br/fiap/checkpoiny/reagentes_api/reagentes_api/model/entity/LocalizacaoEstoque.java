package br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.TipoLocalizacaoEstoque;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidade que representa um local físico de armazenamento.
 */
@Entity
@Table(name = "localizacoes")
public class LocalizacaoEstoque {

    @Id
    private UUID id;

    private String codigoLocal;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoLocalizacaoEstoque tipo;

    private String faixaTemperaturaNominal;

    private String setor;

    public LocalizacaoEstoque() {
        this.id = UUID.randomUUID();
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoLocal() { return codigoLocal; }
    public void setCodigoLocal(String codigoLocal) { this.codigoLocal = codigoLocal; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public TipoLocalizacaoEstoque getTipo() { return tipo; }
    public void setTipo(TipoLocalizacaoEstoque tipo) { this.tipo = tipo; }

    public String getFaixaTemperaturaNominal() { return faixaTemperaturaNominal; }
    public void setFaixaTemperaturaNominal(String faixaTemperaturaNominal) { this.faixaTemperaturaNominal = faixaTemperaturaNominal; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}

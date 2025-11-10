package br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.TipoMovimentacao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade que representa uma movimentação no estoque.
 */
@Entity
@Table(name = "movimentacoes")
public class MovimentacaoEstoque {

    @Id
    private UUID id;

    private LocalDateTime dataHoraMovimentacao;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private Integer quantidadeMovimentada;

    private String observacao;

    // Relação para o reagente afetado (muitos para um)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reagente_id")
    private Reagente reagente;

    public MovimentacaoEstoque() {
        this.id = UUID.randomUUID();
        this.dataHoraMovimentacao = LocalDateTime.now();
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getDataHoraMovimentacao() { return dataHoraMovimentacao; }
    public void setDataHoraMovimentacao(LocalDateTime dataHoraMovimentacao) { this.dataHoraMovimentacao = dataHoraMovimentacao; }

    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }

    public Integer getQuantidadeMovimentada() { return quantidadeMovimentada; }
    public void setQuantidadeMovimentada(Integer quantidadeMovimentada) { this.quantidadeMovimentada = quantidadeMovimentada; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Reagente getReagente() { return reagente; }
    public void setReagente(Reagente reagente) { this.reagente = reagente; }
}

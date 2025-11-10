package br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity;

import br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums.StatusReagente;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade principal Reagente, representando um lote em estoque.
 */
@Entity
@Table(name = "reagentes")
public class Reagente {

    @Id
    private UUID id;

    private String nome;

    private String codigoSku;

    private String lote;

    private LocalDate dataValidade;

    private LocalDate dataRecebimento;

    private Integer quantidadeEmEstoque;

    @Enumerated(EnumType.STRING)
    private StatusReagente status;

    // Relação com Fabricante (muitos reagentes podem ter o mesmo fabricante)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    // Relação com LocalizacaoEstoque
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "localizacao_id")
    private LocalizacaoEstoque localizacao;

    // Movimentacoes associadas (one-to-many)
    @OneToMany(mappedBy = "reagente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

    public Reagente() {
        this.id = UUID.randomUUID();
    }

    // Getters and setters (didaticamente explícitos)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoSku() { return codigoSku; }
    public void setCodigoSku(String codigoSku) { this.codigoSku = codigoSku; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public LocalDate getDataRecebimento() { return dataRecebimento; }
    public void setDataRecebimento(LocalDate dataRecebimento) { this.dataRecebimento = dataRecebimento; }

    public Integer getQuantidadeEmEstoque() { return quantidadeEmEstoque; }
    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) { this.quantidadeEmEstoque = quantidadeEmEstoque; }

    public StatusReagente getStatus() { return status; }
    public void setStatus(StatusReagente status) { this.status = status; }

    public Fabricante getFabricante() { return fabricante; }
    public void setFabricante(Fabricante fabricante) { this.fabricante = fabricante; }

    public LocalizacaoEstoque getLocalizacao() { return localizacao; }
    public void setLocalizacao(LocalizacaoEstoque localizacao) { this.localizacao = localizacao; }

    public List<MovimentacaoEstoque> getMovimentacoes() { return movimentacoes; }
    public void addMovimentacao(MovimentacaoEstoque m) {
        m.setReagente(this);
        this.movimentacoes.add(m);
    }
}

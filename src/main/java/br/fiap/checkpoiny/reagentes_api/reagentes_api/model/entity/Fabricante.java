package br.fiap.checkpoiny.reagentes_api.reagentes_api.model.entity;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidade que representa o Fabricante.
 */
@Entity
@Table(name = "fabricantes")
public class Fabricante {

    @Id
    private UUID id;

    private String nomeOficial;

    private String nomeFantasia;

    private String cnpj;

    private String paisOrigem;

    public Fabricante() {
        this.id = UUID.randomUUID();
    }

    // Getters and setters (didactic - explicit)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNomeOficial() { return nomeOficial; }
    public void setNomeOficial(String nomeOficial) { this.nomeOficial = nomeOficial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }
}

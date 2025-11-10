package br.fiap.checkpoiny.reagentes_api.reagentes_api.model.enums;

/**
 * Enum que descreve os tipos de movimentacao de estoque.
 */
public enum TipoMovimentacao {
    ENTRADA_NOTA,
    SAIDA_USO_ANALISADOR,
    SAIDA_DESCARTE_VENCIMENTO,
    SAIDA_DESCARTE_CONTROLE_QUALIDADE,
    AJUSTE_INVENTARIO_POSITIVO,
    AJUSTE_INVENTARIO_NEGATIVO
}

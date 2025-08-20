package com.ifpb.gestaodegastos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    private TipoRegistro tipo;
    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;
    private String categoria;

    public Registro(String titulo, String descricao, BigDecimal valor, TipoRegistro tipo, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public String getValorString() {
        return valor != null ? valor.toString() : "0.0";
    }

    public void setValorString(String valorStr) {
        try {
            this.valor = new BigDecimal(valorStr);
        } catch (NumberFormatException e) {
            this.valor = BigDecimal.ZERO;
        }
    }
}
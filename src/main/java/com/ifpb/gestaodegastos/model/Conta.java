package com.ifpb.gestaodegastos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Conta {

    public Conta(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.saldo = BigDecimal.ZERO;
        this.orcamento = BigDecimal.ZERO;
        this.orcamentoAtivo = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String senha;
    private BigDecimal saldo;
    private BigDecimal orcamento;
    private boolean orcamentoAtivo;
}
package com.ifpb.gestaodegastos;

import com.ifpb.gestaodegastos.model.Conta;

public class SessaoUsuario {

    private static SessaoUsuario instance;


    private Conta contaLogada;

    private SessaoUsuario() {
    }

    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public void login(Conta conta) {
        this.contaLogada = conta;
    }

    public Conta getContaLogada() {
        return contaLogada;
    }

    public void logout() {
        this.contaLogada = null;
    }

    public boolean isLogado() {
        return contaLogada != null;
    }

}

package com.ifpb.gestaodegastos.Service;

import com.ifpb.gestaodegastos.Dao.ContaDao;
import com.ifpb.gestaodegastos.SessaoUsuario;
import com.ifpb.gestaodegastos.model.Conta;

public class ContaService {

    private ContaDao contaDao;

    public ContaService(){
        this.contaDao = new ContaDao();
    }

    public void cadastrar(String nome, String senha){
        contaDao.salvar(new Conta(nome, senha));
    }
    public Conta buscarPorId(Long id){
        return contaDao.buscarPorId(id);
    }

    public Conta buscarPorUsuario(String nome){
        return contaDao.buscarPorNome(nome);
    }

    public Conta autenticar(String usuario, String senha) {
        Conta conta = contaDao.buscarPorNome(usuario);
        if (conta != null && conta.getSenha().equals(senha)) {
            SessaoUsuario.getInstance().login(conta);
            return conta;
        }
        return null;
    }
}
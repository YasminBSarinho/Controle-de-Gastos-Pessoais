package com.ifpb.gestaodegastos.Service;

import com.ifpb.gestaodegastos.Dao.ContaDao;
import com.ifpb.gestaodegastos.Dao.RegistroDao;
import com.ifpb.gestaodegastos.SessaoUsuario;
import com.ifpb.gestaodegastos.model.Conta;
import com.ifpb.gestaodegastos.model.Registro;
import com.ifpb.gestaodegastos.model.TipoRegistro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RegistroService {

    private final RegistroDao registroDao;
    private final ContaDao contaDao = new ContaDao();
    public RegistroService() {
        this.registroDao = new RegistroDao();
    }

    public void salvarRegistro(String titulo, String descricao, BigDecimal valor, TipoRegistro tipo, String categoria) {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null) return;

        Registro registro = new Registro(titulo, descricao, valor, tipo, categoria);
        registro.setConta(conta);
        if(conta.isOrcamentoAtivo() && registro.getTipo() == TipoRegistro.DESPESA){
            conta.setOrcamento(conta.getOrcamento().subtract(valor));
        }
        contaDao.salvar(conta);

        registroDao.salvar(registro);
    }

    public Registro buscarPorId(Long id) {
        return registroDao.buscarPorId(id);
    }

    public List<Registro> buscarTodosRegistros() {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null){
            return new ArrayList<>();
        }
        return registroDao.buscarTodosRegistros(conta);
    }

    public void editarRegistro(Long id, Registro registro) {
        registroDao.buscarPorId(id);
        if (registro != null) {
            registro.setTitulo(registro.getTitulo());
            registro.setDescricao(registro.getDescricao());
            registro.setValor(registro.getValor());
            registroDao.editar(registro);
        }
    }

    public void deletarRegistro(Long id) {
        registroDao.deletar(id);
    }

    public BigDecimal totalReceitas() {
        BigDecimal total = BigDecimal.ZERO;
        for (Registro r : buscarTodosRegistros()) {
            if (r.getTipo() == TipoRegistro.RECEITA) {
                total = total.add(r.getValor());
            }
        }
        return total;
    }

    public BigDecimal totalDespesas() {
        BigDecimal total = BigDecimal.ZERO;
        for (Registro r : buscarTodosRegistros()) {
            if (r.getTipo() == TipoRegistro.DESPESA) {
                total = total.add(r.getValor());
            }
        }
        return total;
    }
}

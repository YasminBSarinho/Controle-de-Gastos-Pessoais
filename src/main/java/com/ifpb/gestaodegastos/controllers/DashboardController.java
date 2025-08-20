package com.ifpb.gestaodegastos.controllers;

import com.ifpb.gestaodegastos.Dao.ContaDao;
import com.ifpb.gestaodegastos.Service.RegistroService;
import com.ifpb.gestaodegastos.SessaoUsuario;
import com.ifpb.gestaodegastos.model.Conta;
import com.ifpb.gestaodegastos.model.Registro;
import com.ifpb.gestaodegastos.model.TipoRegistro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController extends BasicController {

    @FXML
    private Label textoUsuario;
    @FXML
    private Label textoSaldo;
    @FXML
    private Label labelOrcamento;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private TextField campoOrcamento;

    private final RegistroService registroService = new RegistroService();

    @FXML
    private void initialize() {
        atualizarDashboard();
    }

    public void definirOrcamento(ActionEvent event) {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null) return;
        try {
            BigDecimal valor = new BigDecimal(campoOrcamento.getText());
            if (valor.compareTo(conta.getSaldo()) > 0) {
                labelOrcamento.setText("Valor inválido: não pode ser maior que o saldo");
                return;
            }
            conta.setOrcamento(valor);
            conta.setOrcamentoAtivo(true);
            ContaDao contaDao = new ContaDao();
            contaDao.salvar(conta);
            campoOrcamento.clear();
            atualizarDashboard();
        } catch (NumberFormatException e) {
            labelOrcamento.setText("Valor inválido");
        }
    }

    public void desativarOrcamento(ActionEvent event) {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null){
            return;
        }
        conta.setOrcamentoAtivo(false);
        conta.setOrcamento(BigDecimal.ZERO);
        ContaDao contaDao = new ContaDao();
        contaDao.salvar(conta);
        labelOrcamento.setText("Orçamento desativado");
        atualizarDashboard();
    }

    public void atualizarDashboard() {
        atualizarSaldo();
        atualizarOrcamento();
        popularGrafico();
    }

    private void atualizarSaldo() {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null) return;

        textoUsuario.setText("Usuário: " + conta.getUsuario());

        BigDecimal totalReceitas = registroService.totalReceitas();
        BigDecimal totalDespesas = registroService.totalDespesas();
        BigDecimal saldoAtual = totalReceitas.subtract(totalDespesas);
        conta.setSaldo(saldoAtual);

        textoSaldo.setText("Saldo: R$" + saldoAtual);
    }

    private void atualizarOrcamento() {
        Conta conta = SessaoUsuario.getInstance().getContaLogada();
        if (conta == null || !conta.isOrcamentoAtivo()) {
            labelOrcamento.setText("Orçamento desativado");
            return;
        }
        BigDecimal valor = conta.getOrcamento();

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            labelOrcamento.setText("Orçamento restante: R$0.0 (Excedido!)");
        } else {
            labelOrcamento.setText("Orçamento restante: R$" + valor.toString());
        }
    }

    private void popularGrafico() {
        barChart.getData().clear();
        List<Registro> registros = registroService.buscarTodosRegistros();

        Map<String, Double> despesasPorCategoria = new HashMap<>();
        Map<String, Double> receitasPorCategoria = new HashMap<>();

        for (Registro registro : registros) {
            String categoria = registro.getCategoria() != null ? registro.getCategoria() : "Outro";
            if (registro.getTipo() == TipoRegistro.DESPESA) {
                despesasPorCategoria.put(categoria,
                        despesasPorCategoria.getOrDefault(categoria, 0.0) + registro.getValor().doubleValue());
            } else if (registro.getTipo() == TipoRegistro.RECEITA) {
                receitasPorCategoria.put(categoria,
                        receitasPorCategoria.getOrDefault(categoria, 0.0) + registro.getValor().doubleValue());
            }
        }

        XYChart.Series<String, Number> serieDespesas = new XYChart.Series<>();
        serieDespesas.setName("Despesas");
        despesasPorCategoria.forEach((categoria, valor) -> serieDespesas.getData().add(new XYChart.Data<>(categoria, valor)));

        XYChart.Series<String, Number> serieReceitas = new XYChart.Series<>();
        serieReceitas.setName("Receitas");
        receitasPorCategoria.forEach((categoria, valor) -> serieReceitas.getData().add(new XYChart.Data<>(categoria, valor)));

        barChart.getData().addAll(serieReceitas, serieDespesas);
    }

    @FXML
    private void addRegistro(ActionEvent event) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/registros.fxml"));
        redirect(event, root);
    }

    @FXML
    private void paraRegistros(ActionEvent event) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/tabela.fxml"));
        redirect(event, root);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        SessaoUsuario.getInstance().logout();
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        redirect(event, root);
    }
}
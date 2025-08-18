package com.ifpb.gestaodegastos.controllers;


import com.ifpb.gestaodegastos.Service.RegistroService;
import com.ifpb.gestaodegastos.SessaoUsuario;
import com.ifpb.gestaodegastos.model.Conta;
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

    public void atualizarDashboard() {
        atualizarSaldo();
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
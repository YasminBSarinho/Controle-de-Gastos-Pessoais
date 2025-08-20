package com.ifpb.gestaodegastos.controllers;

import com.ifpb.gestaodegastos.Service.ContaService;
import com.ifpb.gestaodegastos.model.Conta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CadastroController extends BasicController{
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private PasswordField campoConfirmacao;


    ContaService contaService = new ContaService();

    public void paraLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        redirect(event, root);
    }

    public void cadastrar(ActionEvent event) throws IOException {
        String nome = campoUsuario.getText();
        String senha = campoSenha.getText();
        String confirmacao = campoConfirmacao.getText();
        Conta conta = contaService.buscarPorUsuario(nome);

        if(conta != null){
            mostrarAlerta(Alert.AlertType.ERROR, "Já existe um usuário com esse login");
        }
        else if(senha.equals(confirmacao)){
            contaService.cadastrar(nome, senha);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cadastrado com sucesso");
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            redirect(event, root);
        }else {
           mostrarAlerta(Alert.AlertType.ERROR, "Senhas não coincidem");
        }
    }

}
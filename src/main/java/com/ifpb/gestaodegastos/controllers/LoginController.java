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

public class LoginController extends BasicController {
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;

    ContaService contaService = new ContaService();

    public void logar(ActionEvent event) throws IOException {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();
        Conta contaAutenticada = contaService.autenticar(usuario, senha);
        if(contaAutenticada != null){
            root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            redirect(event, root);
        }else {
            mostrarAlerta(Alert.AlertType.ERROR,"Credenciais incorretas");
        }
    }
    public void paraCadastro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/cadastro.fxml"));
        redirect(event, root);
    }

}

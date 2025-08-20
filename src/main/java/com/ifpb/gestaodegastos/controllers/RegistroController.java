package com.ifpb.gestaodegastos.controllers;

import com.ifpb.gestaodegastos.Service.RegistroService;
import com.ifpb.gestaodegastos.model.TipoRegistro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController extends BasicController implements Initializable {

    @FXML
    private TextField campoTitulo;
    @FXML
    private TextField campoDesc;
    @FXML
    private TextField campoValor;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Label tituloLabel;
    @FXML
    private RadioButton radioReceita;
    @FXML
    private RadioButton radioDespesa;

    private RegistroService registroService = new RegistroService();

    private final String[] categoriasReceita = {"Salário", "Extra/Especial"};
    private final String[] categoriasDespesa = {"Alimentação", "Transporte", "Lazer", "Saúde", "Moradia", "Educação", "Outro"};

    private TipoRegistro tipoAtual = TipoRegistro.DESPESA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup grupo = new ToggleGroup();
        radioReceita.setToggleGroup(grupo);
        radioDespesa.setToggleGroup(grupo);

        radioDespesa.setSelected(true);
        atualizarCategorias();

        grupo.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (radioReceita.isSelected()) {
                tipoAtual = TipoRegistro.RECEITA;
                tituloLabel.setText("Registrando Receita");
            } else {
                tipoAtual = TipoRegistro.DESPESA;
                tituloLabel.setText("Registrando Despesa");
            }
            atualizarCategorias();
        });
    }

    private void atualizarCategorias() {
        choiceBox.getItems().clear();
        if (tipoAtual == TipoRegistro.RECEITA) {
            choiceBox.getItems().addAll(categoriasReceita);
        } else {
            choiceBox.getItems().addAll(categoriasDespesa);
        }
        choiceBox.getSelectionModel().selectFirst();
    }

    public void registrar(ActionEvent event) throws IOException {
        String titulo = campoTitulo.getText();
        String descricao = campoDesc.getText();
        BigDecimal valor = new BigDecimal(campoValor.getText());

        registroService.salvarRegistro(titulo, descricao, valor, tipoAtual, choiceBox.getValue());
        mostrarAlerta(Alert.AlertType.INFORMATION, tipoAtual == TipoRegistro.RECEITA ?
                "Receita registrada com sucesso" : "Despesa registrada com sucesso");

        root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
        redirect(event, root);
    }

    public void cancelar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
        redirect(event, root);
    }
}
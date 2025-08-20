package com.ifpb.gestaodegastos.controllers;

import com.ifpb.gestaodegastos.Service.RegistroService;
import com.ifpb.gestaodegastos.model.Registro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;

public class TabelaController extends BasicController {

    @FXML
    private TableView<Registro> tabelaRegistros;

    @FXML
    private TableColumn<Registro, Long> colId;

    @FXML
    private TableColumn<Registro, String> colTipo;

    @FXML
    private TableColumn<Registro, String> colTitulo;

    @FXML
    private TableColumn<Registro, String> colDescricao;

    @FXML
    private TableColumn<Registro, String> colValor;

    private RegistroService registroService = new RegistroService();

    @FXML
    private void initialize() {
        tabelaRegistros.setEditable(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorString"));

        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescricao.setCellFactory(TextFieldTableCell.forTableColumn());
        colValor.setCellFactory(TextFieldTableCell.forTableColumn());

        colTitulo.setOnEditCommit(event -> {
            Registro r = event.getRowValue();
            r.setTitulo(event.getNewValue());
            registroService.editarRegistro(r.getId(), r);
        });

        colDescricao.setOnEditCommit(event -> {
            Registro r = event.getRowValue();
            r.setDescricao(event.getNewValue());
            registroService.editarRegistro(r.getId(), r);
        });

        colValor.setOnEditCommit(event -> {
            Registro r = event.getRowValue();
            r.setValorString(event.getNewValue());
            registroService.editarRegistro(r.getId(), r);
        });

        carregarRegistros();
    }

    private void carregarRegistros() {
        ObservableList<Registro> registros = FXCollections.observableArrayList(
                registroService.buscarTodosRegistros()
        );
        tabelaRegistros.setItems(registros);
    }

    @FXML
    private void deletarSelecionado() {
        Registro selecionado = tabelaRegistros.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            registroService.deletarRegistro(selecionado.getId());
            tabelaRegistros.getItems().remove(selecionado);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um registro para deletar");
        }
    }

    public void voltar(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
        redirect(event, root);
    }
}
package com.ifpb.gestaodegastos;

import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        EntityManagerFactory connection = DB.getConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
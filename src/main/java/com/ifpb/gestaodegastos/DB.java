package com.ifpb.gestaodegastos;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DB {
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getConnection(){
        if(factory != null){
            return factory;
        }
        return Persistence.createEntityManagerFactory("gerencia-gastos");
    }
    public static void closeConnection(){
        factory.close();
    }
}
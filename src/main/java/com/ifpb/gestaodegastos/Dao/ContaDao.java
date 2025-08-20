package com.ifpb.gestaodegastos.Dao;

import com.ifpb.gestaodegastos.DB;
import com.ifpb.gestaodegastos.model.Conta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class ContaDao {

    public void salvar(Conta conta){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(conta);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }

    }

    public Conta buscarPorId(Long id){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        Conta conta =  entityManager.find(Conta.class, id);
        entityManager.close();
        return conta;
    }

    public Conta buscarPorNome(String nome){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        TypedQuery<Conta> query = entityManager.createQuery(
                "SELECT c FROM Conta c WHERE c.usuario = :nome", Conta.class);
        query.setParameter("nome", nome);
        Conta conta = query.getSingleResultOrNull();
        entityManager.close();
        return conta;
    }

}
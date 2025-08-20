package com.ifpb.gestaodegastos.Dao;

import com.ifpb.gestaodegastos.DB;
import com.ifpb.gestaodegastos.model.Conta;
import com.ifpb.gestaodegastos.model.Registro;
import com.ifpb.gestaodegastos.model.TipoRegistro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RegistroDao {

    public void salvar(Registro registro){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(registro);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public Registro buscarPorId(Long id){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        Registro registro = entityManager.find(Registro.class, id);
        entityManager.close();
        return registro;
    }

    public List<Registro> buscarDespesas(Conta conta){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();

        return entityManager
                .createQuery("SELECT r FROM Registro r WHERE r.conta = :conta AND r.tipo =: tipo", Registro.class)
                .setParameter("conta", conta)
                .setParameter("tipo", TipoRegistro.DESPESA)
                .getResultList();
    }

    public List<Registro> buscarReceitas(Conta conta){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();

        return entityManager
                .createQuery("SELECT r FROM Registro r WHERE r.conta = :conta AND r.tipo =: tipo", Registro.class)
                .setParameter("conta", conta)
                .setParameter("tipo", TipoRegistro.RECEITA)
                .getResultList();
    }

    public List<Registro> buscarTodosRegistros(Conta conta){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();

        return entityManager
                .createQuery("SELECT r FROM Registro r WHERE r.conta = :conta", Registro.class)
                .setParameter("conta", conta)
                .getResultList();

    }
    public void deletar(Long id){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        Registro registro = entityManager.find(Registro.class, id);
        if (registro != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(registro);
            entityManager.getTransaction().commit();
        }
    }
    public void editar(Registro registro){
        EntityManagerFactory factory = DB.getConnection();
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(registro);
        entityManager.getTransaction().commit();
    }
}
package com.myagency.travelagencyobjectdb.servicio;

import com.myagency.travelagencyobjectdb.modelo.Turista;
import javax.persistence.*;
import java.util.List;

public class TuristaService {

    private EntityManagerFactory emf;

    public TuristaService() {
        emf = Persistence.createEntityManagerFactory("AgencyTurist");
    }

    public void registrarTurista(Turista turista) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(turista);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al registrar turista: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Turista> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Turista> query = em.createQuery("SELECT t FROM Turista t", Turista.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Turista buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Turista.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizarTurista(Turista turista) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(turista);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar turista: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void eliminarTurista(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Turista turista = em.find(Turista.class, id);
            if (turista != null) {
                em.getTransaction().begin();
                em.remove(turista);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar turista: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

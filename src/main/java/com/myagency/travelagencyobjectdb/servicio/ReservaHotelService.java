package com.myagency.travelagencyobjectdb.servicio;

import com.myagency.travelagencyobjectdb.modelo.ReservaHotel;

import javax.persistence.*;
import java.util.List;

public class ReservaHotelService {

    private EntityManagerFactory emf;

    public ReservaHotelService() {
        emf = Persistence.createEntityManagerFactory("AgencyTurist");
    }

    public void registrarReserva(ReservaHotel reserva) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reserva);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar reserva: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<ReservaHotel> obtenerTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ReservaHotel> query = em.createQuery(
                "SELECT r FROM ReservaHotel r", ReservaHotel.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizarReserva(ReservaHotel reserva) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reserva);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar reserva: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void eliminarReserva(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            ReservaHotel reserva = em.find(ReservaHotel.class, id);
            if (reserva != null) {
                em.getTransaction().begin();
                em.remove(reserva);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar reserva: " + e.getMessage());
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

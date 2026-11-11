
package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Tarea;
import mx.desarrollo.persistence.persistence.hibernateUtils;

import java.util.List;

public class TareaDAO {

    // Registrar una tarea
    public void guardar(Tarea tarea) {
        EntityManager em = hibernateUtils.getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(tarea);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al registrar la tarea", e);

        } finally {
            em.close();
        }
    }

    public List<Tarea> buscarPorProfesor(int idProfesor) {
        EntityManager em = hibernateUtils.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT t FROM Tarea t WHERE t.profesor.id = :idProfesor",
                            Tarea.class)
                    .setParameter("idProfesor", idProfesor)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public void actualizar(Tarea tarea) {
        EntityManager em = hibernateUtils.getEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(tarea);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al actualizar la tarea", e);

        } finally {
            em.close();
        }
    }

    public void cambiarEstado(int idTarea, boolean estado) {
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            Tarea tarea = em.find(Tarea.class, idTarea);

            if (tarea != null) {
                tarea.setCompletado(estado);
                em.merge(tarea);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al cambiar estado de la tarea", e);
        } finally {
            em.close();
        }
    }

    public boolean eliminar(int idTarea, int idProfesor) {
        EntityManager em = hibernateUtils.getEntityManager();

        try {
            em.getTransaction().begin();

            int filasEliminadas = em.createQuery(
                            "DELETE FROM Tarea t " +
                                    "WHERE t.id = :idTarea " +
                                    "AND t.profesor.id = :idProfesor")
                    .setParameter("idTarea", idTarea)
                    .setParameter("idProfesor", idProfesor)
                    .executeUpdate();

            em.getTransaction().commit();

            return filasEliminadas > 0;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al eliminar la tarea", e);

        } finally {
            em.close();
        }
    }
}

package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Tarea;
import mx.desarrollo.persistence.persistence.hibernateUtils;
import java.util.List;

public class TareaDAO {

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //  obtenencion de todas las tareas de un profesor específico
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

    // actualizar una tarea existente, marcarla como completada o cambiar la prioridad)}
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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}

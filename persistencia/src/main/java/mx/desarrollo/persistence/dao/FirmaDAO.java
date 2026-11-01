package mx.desarrollo.persistence.dao;
import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Firma;
import mx.desarrollo.persistence.persistence.hibernateUtils;
import java.util.List;

public class FirmaDAO {

    public boolean guardar(Firma firma) {
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(firma);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Método para obtener todas las firmas de un profesor específico
    public java.util.List<Firma> obtenerPorProfesor(Integer idProfesor) {
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            return em.createQuery("SELECT f FROM Firma f WHERE f.profesor.id = :idProfesor ORDER BY f.fecha DESC, f.hora DESC", Firma.class)
                    .setParameter("idProfesor", idProfesor)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Método para eliminar una firma específica
    public boolean eliminar(Integer idFirma) {
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Firma firma = em.find(Firma.class, idFirma);
            if (firma != null) {
                em.remove(firma);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
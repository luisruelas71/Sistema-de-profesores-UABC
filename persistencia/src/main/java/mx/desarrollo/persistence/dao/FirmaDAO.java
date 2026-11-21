package mx.desarrollo.persistence.dao;
import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Firma;
import mx.desarrollo.persistence.persistence.hibernateUtils;
import java.util.List;
import java.time.LocalDate;

public class FirmaDAO {

    // Registrar firma
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

    // Consulta con ordenación configurable
    public List<Firma> obtenerPorProfesor(Integer idProfesor, String orden) {
        EntityManager em = hibernateUtils.getEntityManager();
        String sentido = ("ASC".equalsIgnoreCase(orden)) ? "ASC" : "DESC";
        try {
            return em.createQuery("SELECT f FROM Firma f WHERE f.profesor.id = :idProfesor " +
                            "ORDER BY f.fecha " + sentido + ", f.hora " + sentido, Firma.class)
                    .setParameter("idProfesor", idProfesor)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Consulta filtrada por fechas y ordenación configurable
    public List<Firma> obtenerPorProfesorYFechas(Integer idProfesor, LocalDate fechaInicio, LocalDate fechaFin, String orden) {
        EntityManager em = hibernateUtils.getEntityManager();
        String sentido = ("ASC".equalsIgnoreCase(orden)) ? "ASC" : "DESC";
        try {
            return em.createQuery("SELECT f FROM Firma f WHERE f.profesor.id = :idProfesor " +
                            "AND f.fecha >= :fechaInicio AND f.fecha <= :fechaFin " +
                            "ORDER BY f.fecha " + sentido + ", f.hora " + sentido, Firma.class)
                    .setParameter("idProfesor", idProfesor)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
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
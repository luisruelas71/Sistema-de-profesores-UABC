package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
import mx.desarrollo.entity.TipoProfesor;
import mx.desarrollo.persistence.persistence.hibernateUtils;

public class TipoProfesorDAO {

    public List<TipoProfesor> listar() {
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM TipoProfesor t ORDER BY t.id", TipoProfesor.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public TipoProfesor buscarPorId(Integer id) {
        if (id == null) {
            return null;
        }
        EntityManager em = hibernateUtils.getEntityManager();
        try {
            return em.find(TipoProfesor.class, id);
        } finally {
            em.close();
        }
    }
}
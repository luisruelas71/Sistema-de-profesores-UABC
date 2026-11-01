package mx.desarrollo.facade;

import mx.desarrollo.entity.Firma;
import mx.desarrollo.persistence.integration.ServiceLocator;

public class FacadeFirma {

    public boolean registrarFirma(Firma firma) {
        if (firma == null || firma.getProfesor() == null || firma.getUbicacion() == null) {
            throw new IllegalArgumentException("Los datos obligatorios de la firma están incompletos");
        }

        return ServiceLocator.getFirmaDAO().guardar(firma);
    }

    public java.util.List<Firma> obtenerPorProfesor(Integer idProfesor) {
        if (idProfesor == null) {
            throw new IllegalArgumentException("El ID del profesor no es válido.");
        }
        return ServiceLocator.getFirmaDAO().obtenerPorProfesor(idProfesor);
    }

    public boolean eliminarFirma(Integer idFirma) {
        if (idFirma == null) {
            throw new IllegalArgumentException("El ID de la firma no es válido.");
        }
        return ServiceLocator.getFirmaDAO().eliminar(idFirma);
    }
}
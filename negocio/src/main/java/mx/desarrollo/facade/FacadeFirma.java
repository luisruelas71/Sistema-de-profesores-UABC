package mx.desarrollo.facade;

import mx.desarrollo.entity.Firma;
import mx.desarrollo.persistence.integration.ServiceLocator;
import java.time.LocalDate;
import java.util.List;

public class FacadeFirma {

    public boolean registrarFirma(Firma firma) {
        if (firma == null || firma.getProfesor() == null || firma.getUbicacion() == null) {
            throw new IllegalArgumentException("Los datos obligatorios de la firma están incompletos");
        }
        return ServiceLocator.getFirmaDAO().guardar(firma);
    }

    public List<Firma> obtenerPorProfesor(Integer idProfesor, String orden) {
        if (idProfesor == null) {
            throw new IllegalArgumentException("El ID del profesor no es válido.");
        }
        return ServiceLocator.getFirmaDAO().obtenerPorProfesor(idProfesor, orden);
    }

    public List<Firma> obtenerPorProfesorYFechas(Integer idProfesor, LocalDate fechaInicio, LocalDate fechaFin, String orden) {
        if (idProfesor == null) {
            throw new IllegalArgumentException("El ID del profesor no es válido.");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
        return ServiceLocator.getFirmaDAO().obtenerPorProfesorYFechas(idProfesor, fechaInicio, fechaFin, orden);
    }

    public boolean eliminarFirma(Integer idFirma) {
        if (idFirma == null) {
            throw new IllegalArgumentException("El ID de la firma no es válido.");
        }
        return ServiceLocator.getFirmaDAO().eliminar(idFirma);
    }
}
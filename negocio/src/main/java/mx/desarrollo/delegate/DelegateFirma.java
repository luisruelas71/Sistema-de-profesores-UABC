package mx.desarrollo.delegate;

import mx.desarrollo.entity.Firma;
import mx.desarrollo.integration.ServiceFacadeLocator;
import java.time.LocalDate;
import java.util.List;

public class DelegateFirma {

    public boolean registrarFirma(Firma firma) {
        return ServiceFacadeLocator.getFacadeFirma().registrarFirma(firma);
    }

    public List<Firma> obtenerPorProfesor(Integer idProfesor, String orden) {
        return ServiceFacadeLocator.getFacadeFirma().obtenerPorProfesor(idProfesor, orden);
    }

    public List<Firma> obtenerPorProfesorYFechas(Integer idProfesor, LocalDate fechaInicio, LocalDate fechaFin, String orden) {
        return ServiceFacadeLocator.getFacadeFirma().obtenerPorProfesorYFechas(idProfesor, fechaInicio, fechaFin, orden);
    }

    public boolean eliminarFirma(Integer idFirma) {
        return ServiceFacadeLocator.getFacadeFirma().eliminarFirma(idFirma);
    }
}
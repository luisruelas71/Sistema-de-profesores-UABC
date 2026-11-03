package mx.desarrollo.delegate;

import mx.desarrollo.entity.Firma;
import mx.desarrollo.integration.ServiceFacadeLocator;

public class DelegateFirma {

    public boolean registrarFirma(Firma firma) {
        return ServiceFacadeLocator.getFacadeFirma().registrarFirma(firma);
    }

    public java.util.List<Firma> obtenerPorProfesor(Integer idProfesor) {
        return ServiceFacadeLocator.getFacadeFirma().obtenerPorProfesor(idProfesor);
    }

    public boolean eliminarFirma(Integer idFirma) {
        return ServiceFacadeLocator.getFacadeFirma().eliminarFirma(idFirma);
    }
}
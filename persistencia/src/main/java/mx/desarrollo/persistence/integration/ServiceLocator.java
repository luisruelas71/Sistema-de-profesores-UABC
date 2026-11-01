package mx.desarrollo.persistence.integration;

import mx.desarrollo.persistence.dao.FirmaDAO;
import mx.desarrollo.persistence.dao.profesorDAO;


public class ServiceLocator {

    private static profesorDAO daoProfesor;
    private static FirmaDAO daoFirma;

    private ServiceLocator() {
    }

    public static synchronized profesorDAO getProfesorDAO() {
        if (daoProfesor == null) {
            daoProfesor = new profesorDAO();
        }
        return daoProfesor;
    }

    public static synchronized FirmaDAO getFirmaDAO() {
        if (daoFirma == null) {
            daoFirma = new FirmaDAO();
        }
        return daoFirma;
    }
}
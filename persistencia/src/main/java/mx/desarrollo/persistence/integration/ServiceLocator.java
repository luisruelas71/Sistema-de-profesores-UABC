package mx.desarrollo.persistence.integration;

import mx.desarrollo.persistence.dao.FirmaDAO;
import mx.desarrollo.persistence.dao.profesorDAO;
import mx.desarrollo.persistence.dao.TareaDAO;


public class ServiceLocator {

    private static profesorDAO daoProfesor;
    private static FirmaDAO daoFirma;
    private static TareaDAO daoTarea;

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

    public static synchronized TareaDAO getTareaDAO() {
        if (daoTarea == null) {
            daoTarea = new TareaDAO();
        }

        return daoTarea;
    }
}
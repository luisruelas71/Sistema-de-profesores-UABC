package mx.desarrollo.persistence.integration;

import mx.desarrollo.persistence.dao.FirmaDAO;
import mx.desarrollo.persistence.dao.RegistroPendienteDAO;
import mx.desarrollo.persistence.dao.TareaDAO;
import mx.desarrollo.persistence.dao.TipoProfesorDAO;
import mx.desarrollo.persistence.dao.profesorDAO;

public class ServiceLocator {

    private static profesorDAO daoProfesor;
    private static FirmaDAO daoFirma;
    private static TareaDAO daoTarea;
    private static TipoProfesorDAO daoTipoProfesor;
    private static RegistroPendienteDAO daoRegistroPendiente;

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

    public static synchronized TipoProfesorDAO getTipoProfesorDAO() {
        if (daoTipoProfesor == null) {
            daoTipoProfesor = new TipoProfesorDAO();
        }
        return daoTipoProfesor;
    }

    public static synchronized RegistroPendienteDAO getRegistroPendienteDAO() {
        if (daoRegistroPendiente == null) {
            daoRegistroPendiente = new RegistroPendienteDAO();
        }
        return daoRegistroPendiente;
    }
}
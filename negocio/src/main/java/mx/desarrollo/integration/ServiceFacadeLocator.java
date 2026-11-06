package mx.desarrollo.integration;

import mx.desarrollo.facade.FacadeProfesor;
import mx.desarrollo.facade.FacadeFirma;
import mx.desarrollo.facade.FacadeTarea;

public class ServiceFacadeLocator {

    private static FacadeProfesor facadeProfesor;
    private static FacadeFirma facadeFirma;
    private static FacadeTarea facadeTarea;

    private ServiceFacadeLocator() {
    }

    public static synchronized FacadeProfesor getFacadeProfesor() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
        }
        return facadeProfesor;
    }

    public static synchronized FacadeFirma getFacadeFirma() {
        if (facadeFirma == null) {
            facadeFirma = new FacadeFirma();
        }
        return facadeFirma;
    }

    public static synchronized FacadeTarea getFacadeTarea() {
        if (facadeTarea == null) {
            facadeTarea = new FacadeTarea();
        }
        return facadeTarea;
    }
}
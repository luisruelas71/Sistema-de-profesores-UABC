package mx.desarrollo.delegate;

import mx.desarrollo.entity.Tarea;
import mx.desarrollo.integration.ServiceFacadeLocator;

public class DelegateTarea {

    public void registrarTarea(Tarea tarea) {
        ServiceFacadeLocator.getFacadeTarea().registrarTarea(tarea);
    }
}
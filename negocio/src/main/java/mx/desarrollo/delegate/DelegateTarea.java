package mx.desarrollo.delegate;

import mx.desarrollo.entity.Tarea;
import mx.desarrollo.integration.ServiceFacadeLocator;

import java.util.List;

public class DelegateTarea {

    // Registrar una tarea
    public void registrarTarea(Tarea tarea) {
        ServiceFacadeLocator.getFacadeTarea().registrarTarea(tarea);
    }

    // Consultar tareas de un profesor
    public List<Tarea> consultarTareas(int idProfesor) {
        return ServiceFacadeLocator.getFacadeTarea().consultarTareas(idProfesor);
    }

    // Eliminar una tarea
    public boolean eliminarTarea(int idTarea, int idProfesor) {
        return ServiceFacadeLocator.getFacadeTarea().eliminarTarea(idTarea, idProfesor);
    }
}
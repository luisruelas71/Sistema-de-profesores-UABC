package mx.desarrollo.delegate;

import mx.desarrollo.entity.Tarea;
import mx.desarrollo.integration.ServiceFacadeLocator;

import java.util.List;

public class DelegateTarea {

    public void registrarTarea(Tarea tarea) {
        ServiceFacadeLocator.getFacadeTarea().registrarTarea(tarea);
    }

    public List<Tarea> consultarTareas(int idProfesor) {
        return ServiceFacadeLocator.getFacadeTarea().consultarTareas(idProfesor);
    }

    public boolean eliminarTarea(int idTarea, int idProfesor) {
        return ServiceFacadeLocator.getFacadeTarea().eliminarTarea(idTarea, idProfesor);
    }

    public boolean actualizarTarea(Tarea tarea, int idProfesor) {
        return ServiceFacadeLocator.getFacadeTarea()
                .actualizarTarea(tarea, idProfesor);
    }

    public void cambiarEstado(int idTarea, boolean estado) {
        mx.desarrollo.integration.ServiceFacadeLocator.getFacadeTarea().cambiarEstado(idTarea, estado);
    }
}

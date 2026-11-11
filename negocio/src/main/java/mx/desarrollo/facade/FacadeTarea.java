package mx.desarrollo.facade;

import mx.desarrollo.entity.Tarea;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;

public class FacadeTarea {

    public void registrarTarea(Tarea tarea) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }

        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio");
        }

        if (tarea.getDescripcion() == null || tarea.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción de la tarea es obligatoria");
        }

        ServiceLocator.getTareaDAO().guardar(tarea);
    }

    public List<Tarea> consultarTareas(int idProfesor) {
        return ServiceLocator.getTareaDAO().buscarPorProfesor(idProfesor);
    }

    public boolean eliminarTarea(int idTarea, int idProfesor) {
        return ServiceLocator.getTareaDAO().eliminar(idTarea, idProfesor);
    }

    public void actualizarTarea(Tarea tarea) {
        mx.desarrollo.persistence.integration.ServiceLocator.getTareaDAO().actualizar(tarea);
    }

    public void cambiarEstado(int idTarea, boolean estado) {
        mx.desarrollo.persistence.integration.ServiceLocator.getTareaDAO().cambiarEstado(idTarea, estado);
    }
}


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

    public boolean actualizarTarea(Tarea tarea, int idProfesor) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }

        if (tarea.getId() == null) {
            throw new IllegalArgumentException("La tarea no tiene un identificador");
        }

        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }

        if (tarea.getTitulo().length() > 50) {
            throw new IllegalArgumentException("El título no puede superar 50 caracteres");
        }

        if (tarea.getDescripcion() == null || tarea.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }

        if (tarea.getDescripcion().length() > 200) {
            throw new IllegalArgumentException("La descripción no puede superar 200 caracteres");
        }

        if (tarea.getFechaLimite() == null || tarea.getPrioridad() == null) {
            throw new IllegalArgumentException("La fecha y la prioridad son obligatorias");
        }

        return ServiceLocator.getTareaDAO().actualizar(tarea, idProfesor);
    }

    public void cambiarEstado(int idTarea, boolean estado) {
        mx.desarrollo.persistence.integration.ServiceLocator.getTareaDAO().cambiarEstado(idTarea, estado);
    }
}


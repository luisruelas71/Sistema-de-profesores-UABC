package mx.desarrollo.facade;

import mx.desarrollo.entity.Tarea;
import mx.desarrollo.persistence.integration.ServiceLocator;

public class FacadeTarea {

    public void registrarTarea(Tarea tarea) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }
        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio");
        }

        if (tarea.getDescripcion() == null || tarea.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripcion de la tarea es obligatorio");
        }


        ServiceLocator.getTareaDAO().guardar(tarea);
    }
}
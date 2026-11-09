package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.desarrollo.delegate.DelegateTarea;
import mx.desarrollo.entity.Tarea;

@Named("historialTareasController")
@RequestScoped
public class HistorialTareas implements Serializable {

    private List<Tarea> tareas = new ArrayList<>();
    private final DelegateTarea delegate = new DelegateTarea();

    @PostConstruct
    public void cargarTareas() {
        Map<String, Object> sesion = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();

        Integer idProfesor = (Integer) sesion.get("profesorId");

        if (idProfesor != null) {
            tareas = delegate.consultarTareas(idProfesor);
        }
    }

    public void eliminarTarea(Integer idTarea) {
        try {
            Map<String, Object> sesion = FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap();

            Integer idProfesor = (Integer) sesion.get("profesorId");

            if (idProfesor == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                        "No hay una sesiÃ³n activa.");
                return;
            }

            boolean eliminada = delegate.eliminarTarea(idTarea, idProfesor);

            if (eliminada) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO,
                        "Tarea eliminada correctamente.");
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_WARN,
                        "No se encontrÃ³ la tarea.");
            }

            cargarTareas();

        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                    "OcurriÃ³ un error al eliminar la tarea.");
        }
    }

    private void mostrarMensaje(FacesMessage.Severity tipo, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(tipo, mensaje, null));
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}


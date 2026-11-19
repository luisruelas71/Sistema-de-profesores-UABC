package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped; // <-- CAMBIO DE IMPORT
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.desarrollo.delegate.DelegateTarea;
import mx.desarrollo.entity.Tarea;

@Named("historialTareasController")
@ViewScoped
public class HistorialTareas implements Serializable {


    private List<Tarea> tareas = new ArrayList<>();

    private List<Tarea> tareasFiltradas;

    private final DelegateTarea delegate = new DelegateTarea();

    private Tarea tareaSeleccionada;
    private Integer idTareaSeleccionada;

    @PostConstruct
    public void cargarTareas() {
        Map<String, Object> sesion = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();

        Integer idProfesor = (Integer) sesion.get("profesorId");

        if (idProfesor != null) {
            tareas = delegate.consultarTareas(idProfesor);

            tareasFiltradas = null;
        }
    }

    public void seleccionarTarea(Tarea tarea) {
        this.tareaSeleccionada = tarea;
        this.idTareaSeleccionada = tarea.getId();
    }

    public void eliminarTarea(Integer idTarea) {
        try {
            Map<String, Object> sesion = FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap();

            Integer idProfesor = (Integer) sesion.get("profesorId");

            if (idProfesor == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                        "No hay una sesión activa.");
                return;
            }

            boolean eliminada = delegate.eliminarTarea(idTarea, idProfesor);

            if (eliminada) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO,
                        "Tarea eliminada correctamente.");
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_WARN,
                        "No se encontró la tarea.");
            }

            cargarTareas();

        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                    "Ocurrió un error al eliminar la tarea.");
        }
    }

    public void cambiarEstado(Integer idTarea, boolean completado) {
        try {
            delegate.cambiarEstado(idTarea, completado);

            String msj = completado ? "La tarea ha sido marcada como completada." : "La tarea ha vuelto a pendiente.";
            String titulo = completado ? "Éxito" : "Aviso";

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, msj));

            cargarTareas();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el estado."));
        }
    }

    public void actualizarTarea() {
        try {
            Map<String, Object> sesion = FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap();

            Integer idProfesor = (Integer) sesion.get("profesorId");

            if (idProfesor == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                        "No hay una sesión activa.");
                return;
            }

            if (tareaSeleccionada == null || idTareaSeleccionada == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                        "No se ha seleccionado ninguna tarea.");
                return;
            }

            tareaSeleccionada.setId(idTareaSeleccionada);

            boolean actualizada = delegate.actualizarTarea(
                    tareaSeleccionada, idProfesor);

            if (actualizada) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO,
                        "Tarea actualizada correctamente.");

                cargarTareas();

            } else {
                mostrarMensaje(FacesMessage.SEVERITY_WARN,
                        "No se encontró la tarea o no pertenece a tu usuario.");
            }

        } catch (IllegalArgumentException e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje(FacesMessage.SEVERITY_ERROR,
                    "Ocurrió un error al actualizar la tarea.");
        }
    }

    private void mostrarMensaje(FacesMessage.Severity tipo, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(tipo, mensaje, null));
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Tarea> getTareasFiltradas() {
        return tareasFiltradas;
    }

    public void setTareasFiltradas(List<Tarea> tareasFiltradas) {
        this.tareasFiltradas = tareasFiltradas;
    }

    public Tarea getTareaSeleccionada() {
        return tareaSeleccionada;
    }

    public void setTareaSeleccionada(Tarea tareaSeleccionada) {
        this.tareaSeleccionada = tareaSeleccionada;
    }

    public Integer getIdTareaSeleccionada() {
        return idTareaSeleccionada;
    }

    public void setIdTareaSeleccionada(Integer idTareaSeleccionada) {
        this.idTareaSeleccionada = idTareaSeleccionada;
    }
}
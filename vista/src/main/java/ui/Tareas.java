package ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import mx.desarrollo.delegate.DelegateTarea;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.Tarea;

@Named("tareasController")
@RequestScoped
public class Tareas implements Serializable {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaLimite;
    private String prioridad;

    public String guardarTarea() {
        try {
            // obtiene la ID del profesor desde la sesión
            Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Integer idProfesor = (Integer) sesion.get("profesorId");

            if (idProfesor == null) {
                mostrarError("No hay una sesión activa. Por favor, inicia sesión nuevamente.");
                return "login?faces-redirect=true";
            }

            Tarea nuevaTarea = new Tarea();
            nuevaTarea.setTitulo(this.titulo);
            nuevaTarea.setDescripcion(this.descripcion);
            nuevaTarea.setFechaLimite(this.fechaLimite);
            nuevaTarea.setPrioridad(this.prioridad);
            nuevaTarea.setCompletado(false);

            Profesor profesor = new Profesor();
            profesor.setId(idProfesor);
            nuevaTarea.setProfesor(profesor);


            DelegateTarea delegate = new DelegateTarea();
            delegate.registrarTarea(nuevaTarea);

            // muestra el  mensaje de éxito y limpiar el formulario
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarea guardada correctamente", null));
            limpiarCampos();

            // Return a la misma página o redirigir a donde se necesite
            return null;

        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al guardar la tarea. Intenta más tarde.");
            return null;
        }
    }

    private void mostrarError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
    }

    private void limpiarCampos() {
        this.titulo = null;
        this.descripcion = null;
        this.fechaLimite = null;
        this.prioridad = null;
    }


    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDateTime fechaLimite) { this.fechaLimite = fechaLimite; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
}
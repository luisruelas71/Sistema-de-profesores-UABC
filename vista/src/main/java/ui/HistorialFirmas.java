package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import mx.desarrollo.delegate.DelegateFirma;
import mx.desarrollo.entity.Firma;

@Named("historialFirmasController")
@ViewScoped
public class HistorialFirmas implements Serializable {

    private List<Firma> firmas;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String ordenFecha = "DESC"; // Valor por defecto: Descendente (más reciente primero)

    private DelegateFirma delegate = new DelegateFirma();

    @PostConstruct
    public void init() {
        cargarFirmas();
    }

    public void cargarFirmas() {
        Integer profesorId = obtenerProfesorSesion();
        if (profesorId != null) {
            if (fechaInicio != null && fechaFin != null) {
                try {
                    firmas = delegate.obtenerPorProfesorYFechas(profesorId, fechaInicio, fechaFin, ordenFecha);
                } catch (IllegalArgumentException e) {
                    mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error de validación", e.getMessage());
                }
            } else {
                firmas = delegate.obtenerPorProfesor(profesorId, ordenFecha);
            }
        }
    }

    public void filtrar() {
        if ((fechaInicio != null && fechaFin == null) || (fechaInicio == null && fechaFin != null)) {
            mostrarMensaje(FacesMessage.SEVERITY_WARN, "Advertencia", "Debes seleccionar ambas fechas para filtrar por rango.");
            return;
        }

        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "La fecha inicial no puede ser posterior a la fecha final.");
            return;
        }

        cargarFirmas();
    }

    public void limpiarFiltro() {
        this.fechaInicio = null;
        this.fechaFin = null;
        this.ordenFecha = "DESC";
        cargarFirmas();
    }

    public void eliminarFirma(Integer idFirma) {
        boolean exito = delegate.eliminarFirma(idFirma);
        if (exito) {
            cargarFirmas();
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Firma eliminada correctamente.");
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la firma.");
        }
    }

    private Integer obtenerProfesorSesion() {
        Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object idObj = sesion.get("profesorId");
        if (idObj != null) {
            return (idObj instanceof Integer) ? (Integer) idObj : Integer.parseInt(idObj.toString());
        }
        return null;
    }

    private void mostrarMensaje(FacesMessage.Severity severidad, String titulo, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, titulo, mensaje));
    }

    // Getters y Setters
    public List<Firma> getFirmas() { return firmas; }
    public void setFirmas(List<Firma> firmas) { this.firmas = firmas; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getOrdenFecha() { return ordenFecha; }
    public void setOrdenFecha(String ordenFecha) { this.ordenFecha = ordenFecha; }
}
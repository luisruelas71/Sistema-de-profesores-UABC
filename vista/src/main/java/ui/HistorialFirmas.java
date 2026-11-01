package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import mx.desarrollo.delegate.DelegateFirma;
import mx.desarrollo.entity.Firma;

@Named("historialFirmasController")
@ViewScoped
public class HistorialFirmas implements Serializable {

    private List<Firma> firmas;
    private DelegateFirma delegate = new DelegateFirma();

    @PostConstruct
    public void init() {
        cargarFirmas();
    }

    public void cargarFirmas() {
        Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object idObj = sesion.get("profesorId");

        if (idObj != null) {
            Integer profesorId = (idObj instanceof Integer) ? (Integer) idObj : Integer.parseInt(idObj.toString());
            firmas = delegate.obtenerPorProfesor(profesorId);
        }
    }

    public void eliminarFirma(Integer idFirma) {
        boolean exito = delegate.eliminarFirma(idFirma);
        if (exito) {
            cargarFirmas(); // Recarga la tabla automáticamente
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Firma eliminada correctamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la firma."));
        }
    }

    public List<Firma> getFirmas() { return firmas; }
    public void setFirmas(List<Firma> firmas) { this.firmas = firmas; }
}
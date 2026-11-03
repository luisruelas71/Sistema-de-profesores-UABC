package ui;

import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import mx.desarrollo.delegate.DelegateFirma;
import mx.desarrollo.entity.Profesor;

@Named("firmaController")

@ViewScoped

public class Firma implements Serializable {

    private String ubicacion;
    private byte[] fotografia;

    public String tomarFoto() {
        System.out.println("Tomando foto...");
        return null;
    }

    public String descartarFoto() {
        this.fotografia = null;
        return null;
    }

    // "Aceptar" redije a guardar firmar en registrar_firma.xhtml
    public String aceptarFoto() {
        return "registrar_firma?faces-redirect=true";
    }

    public String cerrar() {
        return "firmas?faces-redirect=true";
    }

    public String guardarFirma() {
        Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        if (this.fotografia == null) {
            this.fotografia = new byte[0]; // Arreglo vacío simulando una foto en blanco
        }

        Object idObj = sesion.get("profesorId");
        if (idObj == null) {
            System.out.println("-> ERROR FIRMA: No hay sesión de profesor activa.");
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "La sesión ha expirado.");
            return "login?faces-redirect=true";
        }

        Integer profesorId = (idObj instanceof Integer) ? (Integer) idObj : Integer.parseInt(idObj.toString());


        if (this.ubicacion == null || this.ubicacion.isBlank()) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "No se capturó la ubicación GPS.");
            return null;
        }

        try {
            Profesor profesor = new Profesor();
            profesor.setId(profesorId);

            mx.desarrollo.entity.Firma firmaEntidad = new mx.desarrollo.entity.Firma();
            firmaEntidad.setProfesor(profesor);
            firmaEntidad.setFecha(LocalDate.now());
            firmaEntidad.setHora(LocalTime.now());
            firmaEntidad.setUbicacion(this.ubicacion);
            firmaEntidad.setFotografia(this.fotografia);

            boolean exito = new DelegateFirma().registrarFirma(firmaEntidad);

            if (exito) {
                return "firmas?faces-redirect=true";
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "No se pudo guardar la firma.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error interno al guardar: " + e.getMessage());
            return null;
        }
    }

    private void mostrarMensaje(FacesMessage.Severity severidad, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, mensaje, null));
    }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public byte[] getFotografia() { return fotografia; }
    public void setFotografia(byte[] fotografia) { this.fotografia = fotografia; }

    public String rechazarFirma() {
        this.fotografia = null;
        this.ubicacion = null;

        mostrarMensaje(FacesMessage.SEVERITY_WARN, "Cancelado: No se registró la firma.");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "captura_foto?faces-redirect=true";
    }

    public String guardarFirmaFinal() {
        String resultado = guardarFirma();

        if ("firmas?faces-redirect=true".equals(resultado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito: La firma se registró correctamente.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }

        return resultado;
    }
}
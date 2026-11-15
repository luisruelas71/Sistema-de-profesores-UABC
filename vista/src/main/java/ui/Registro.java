package ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import mx.desarrollo.delegate.DelegateRegistro;
import mx.desarrollo.entity.TipoProfesor;

@Named("registroController")
@RequestScoped
public class Registro implements Serializable {

    private static final String CLAVE_CORREO = "registroCorreo";

    private String correo;
    private String contrasena;
    private String confirmacion;
    private Integer idTipo;
    private String codigo;
    private List<TipoProfesor> tipos;

    public String solicitarRegistro() {
        if (vacio(correo) || vacio(contrasena) || vacio(confirmacion) || idTipo == null) {
            mostrar(FacesMessage.SEVERITY_ERROR, "Completa todos los campos");
            return null;
        }
        try {
            new DelegateRegistro().solicitarRegistro(correo, contrasena, confirmacion, idTipo);
            contexto().getSessionMap().put(CLAVE_CORREO, correo.trim().toLowerCase());
            return "verificar_codigo?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            mostrar(FacesMessage.SEVERITY_ERROR, e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            mostrar(FacesMessage.SEVERITY_ERROR, "No se pudo crear la cuenta. Intenta más tarde.");
            return null;
        }
    }

    public String verificarCodigo() {
        String correoPendiente = getCorreoPendiente();
        if (correoPendiente == null) {
            return "registro?faces-redirect=true";
        }
        if (vacio(codigo)) {
            mostrar(FacesMessage.SEVERITY_ERROR, "Escribe el código de 6 dígitos");
            return null;
        }
        try {
            new DelegateRegistro().verificarCodigo(correoPendiente, codigo);
            contexto().getSessionMap().remove(CLAVE_CORREO);
            contexto().getFlash().setKeepMessages(true);
            mostrar(FacesMessage.SEVERITY_INFO, "Cuenta creada. Ya puedes iniciar sesión");
            return "login?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            mostrar(FacesMessage.SEVERITY_ERROR, e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            mostrar(FacesMessage.SEVERITY_ERROR, "No se pudo verificar el código. Intenta más tarde.");
            return null;
        }
    }

    public String reenviarCodigo() {
        String correoPendiente = getCorreoPendiente();
        if (correoPendiente == null) {
            return "registro?faces-redirect=true";
        }
        try {
            new DelegateRegistro().reenviarCodigo(correoPendiente);
            mostrar(FacesMessage.SEVERITY_INFO, "Te enviamos un código nuevo");
        } catch (IllegalArgumentException e) {
            mostrar(FacesMessage.SEVERITY_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrar(FacesMessage.SEVERITY_ERROR, "No se pudo reenviar el código. Intenta más tarde.");
        }
        return null;
    }

    public String cambiarCorreo() {
        contexto().getSessionMap().remove(CLAVE_CORREO);
        return "registro?faces-redirect=true";
    }

    public void validarPantallaVerificacion() {
        if (getCorreoPendiente() == null) {
            try {
                contexto().redirect(contexto().getRequestContextPath() + "/registro.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCorreoPendiente() {
        return (String) contexto().getSessionMap().get(CLAVE_CORREO);
    }

    public List<TipoProfesor> getTipos() {
        if (tipos == null) {
            tipos = new DelegateRegistro().listarTipos();
        }
        return tipos;
    }

    private ExternalContext contexto() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    private void mostrar(FacesMessage.Severity nivel, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(nivel, mensaje, null));
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getConfirmacion() { return confirmacion; }
    public void setConfirmacion(String confirmacion) { this.confirmacion = confirmacion; }

    public Integer getIdTipo() { return idTipo; }
    public void setIdTipo(Integer idTipo) { this.idTipo = idTipo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
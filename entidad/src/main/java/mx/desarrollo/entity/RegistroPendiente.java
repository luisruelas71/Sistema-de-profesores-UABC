package mx.desarrollo.entity;

import java.time.LocalDateTime;




public class RegistroPendiente {

    private final String correo;
    private final String contrasenaHash;
    private final Integer idTipo;
    private String codigoHash;
    private LocalDateTime vence;
    private int intentos;
    private int envios;
    private LocalDateTime ultimoEnvio;

    public RegistroPendiente(String correo, String contrasenaHash, Integer idTipo,
                             String codigoHash, LocalDateTime vence) {
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.idTipo = idTipo;
        this.codigoHash = codigoHash;
        this.vence = vence;
        this.envios = 1;
        this.ultimoEnvio = LocalDateTime.now();
    }

    public boolean estaVencido() { return LocalDateTime.now().isAfter(vence); }
    public synchronized int sumarIntento() { return ++intentos; }

    public String getCorreo() { return correo; }
    public String getContrasenaHash() { return contrasenaHash; }
    public Integer getIdTipo() { return idTipo; }
    public String getCodigoHash() { return codigoHash; }
    public void setCodigoHash(String codigoHash) { this.codigoHash = codigoHash; }
    public LocalDateTime getVence() { return vence; }
    public void setVence(LocalDateTime vence) { this.vence = vence; }
    public int getIntentos() { return intentos; }
    public void setIntentos(int intentos) { this.intentos = intentos; }
    public int getEnvios() { return envios; }
    public void setEnvios(int envios) { this.envios = envios; }
    public LocalDateTime getUltimoEnvio() { return ultimoEnvio; }
    public void setUltimoEnvio(LocalDateTime ultimoEnvio) { this.ultimoEnvio = ultimoEnvio; }
}

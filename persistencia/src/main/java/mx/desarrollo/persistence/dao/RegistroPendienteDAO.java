package mx.desarrollo.persistence.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mx.desarrollo.entity.RegistroPendiente;

/** Guarda las solicitudes pendientes en memoria (sin tabla en la base de datos). */
public class RegistroPendienteDAO {

    private static final Map<String, RegistroPendiente> PENDIENTES = new ConcurrentHashMap<>();

    public void guardarOReemplazar(RegistroPendiente pendiente) {
        PENDIENTES.values().removeIf(RegistroPendiente::estaVencido);
        PENDIENTES.put(pendiente.getCorreo(), pendiente);
    }

    public RegistroPendiente buscarPorCorreo(String correo) {
        return correo == null ? null : PENDIENTES.get(correo);
    }

    /** Suma un intento fallido y devuelve el total. */
    public int sumarIntento(String correo) {
        RegistroPendiente pendiente = buscarPorCorreo(correo);
        return pendiente == null ? 0 : pendiente.sumarIntento();
    }

    public void eliminar(String correo) {
        if (correo != null) {
            PENDIENTES.remove(correo);
        }
    }
}
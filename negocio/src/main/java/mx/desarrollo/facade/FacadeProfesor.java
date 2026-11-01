package mx.desarrollo.facade;

import java.util.regex.Pattern;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistence.integration.ServiceLocator;
import org.mindrot.jbcrypt.BCrypt;

public class FacadeProfesor {

    private static final Pattern FORMATO_CORREO =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Profesor iniciarSesion(String correo, String contrasena) {
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Completa todos los campos");
        }
        String correoLimpio = correo.trim();
        if (correoLimpio.length() > 60 || !FORMATO_CORREO.matcher(correoLimpio).matches()) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }
        Profesor profesor = ServiceLocator.getProfesorDAO().buscarPorCorreo(correoLimpio);
        if (profesor == null || !contrasenaValida(contrasena, profesor.getContrasena())) {
            return null;
        }
        return profesor;
    }

    private boolean contrasenaValida(String plana, String hash) {
        if (plana == null || hash == null) {
            return false;
        }
        String planaLimpia = plana.trim();
        String hashLimpio = hash.trim();
        if (planaLimpia.equals(hashLimpio)) {  // Comparación directa para texto plano
            return true;
        }
        try {         // Comparación por BCrypt si está encriptada
            return BCrypt.checkpw(planaLimpia, hashLimpio);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
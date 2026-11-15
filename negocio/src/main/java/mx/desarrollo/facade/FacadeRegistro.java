package mx.desarrollo.facade;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.RegistroPendiente;
import mx.desarrollo.entity.TipoProfesor;
import mx.desarrollo.persistence.dao.RegistroPendienteDAO;
import mx.desarrollo.persistence.integration.ServiceLocator;
import org.mindrot.jbcrypt.BCrypt;

public class FacadeRegistro {

    private static final String DOMINIO = "@uabc.edu.mx";
    private static final Pattern CORREO_UABC = Pattern.compile("^[a-z0-9._%+-]+@uabc\\.edu\\.mx$");
    private static final int MINUTOS_VIGENCIA = 10;
    private static final int MAX_INTENTOS = 5;
    private static final int MAX_ENVIOS = 3;
    private static final int ESPERA_SEGUNDOS = 60;

    private final SecureRandom random = new SecureRandom();

    public List<TipoProfesor> listarTipos() {
        return ServiceLocator.getTipoProfesorDAO().listar();
    }

    public void solicitarRegistro(String correo, String contrasena, String confirmacion, Integer idTipo) {
        String clave = contrasena == null ? null : contrasena.trim();   // el login también recorta espacios
        String claveConfirmada = confirmacion == null ? null : confirmacion.trim();
        validarDatos(correo, clave, claveConfirmada, idTipo);

        String correoLimpio = normalizar(correo);
        if (ServiceLocator.getProfesorDAO().buscarPorCorreo(correoLimpio) != null) {
            throw new IllegalArgumentException("Este correo ya tiene una cuenta");
        }
        if (ServiceLocator.getTipoProfesorDAO().buscarPorId(idTipo) == null) {
            throw new IllegalArgumentException("Selecciona un tipo de profesor válido");
        }

        String codigo = generarCodigo();
        RegistroPendienteDAO dao = ServiceLocator.getRegistroPendienteDAO();
        dao.guardarOReemplazar(new RegistroPendiente(
                correoLimpio,
                BCrypt.hashpw(clave, BCrypt.gensalt(10)),
                idTipo,
                BCrypt.hashpw(codigo, BCrypt.gensalt(10)),
                LocalDateTime.now().plusMinutes(MINUTOS_VIGENCIA)));

        try {
            ServicioCorreo.enviarCodigo(correoLimpio, codigo);
        } catch (Exception e) {
            e.printStackTrace();
            dao.eliminar(correoLimpio);
            throw new IllegalArgumentException("No se pudo enviar el código. Intenta más tarde.");
        }
    }

    public void verificarCodigo(String correo, String codigo) {
        if (codigo == null || !codigo.trim().matches("\\d{6}")) {
            throw new IllegalArgumentException("Escribe el código de 6 dígitos");
        }
        String correoLimpio = normalizar(correo);
        RegistroPendienteDAO dao = ServiceLocator.getRegistroPendienteDAO();
        RegistroPendiente pendiente = dao.buscarPorCorreo(correoLimpio);

        if (pendiente == null) {
            throw new IllegalArgumentException("No encontramos tu solicitud. Vuelve a registrarte");
        }
        if (pendiente.estaVencido() || pendiente.getIntentos() >= MAX_INTENTOS) {
            dao.eliminar(correoLimpio);
            throw new IllegalArgumentException("El código venció o se agotaron los intentos. Vuelve a registrarte");
        }
        if (!BCrypt.checkpw(codigo.trim(), pendiente.getCodigoHash())) {
            int restantes = MAX_INTENTOS - dao.sumarIntento(correoLimpio);
            if (restantes <= 0) {
                dao.eliminar(correoLimpio);
                throw new IllegalArgumentException("Se agotaron los intentos. Vuelve a registrarte");
            }
            throw new IllegalArgumentException("Código incorrecto. Intentos restantes: " + restantes);
        }

        Profesor profesor = new Profesor();
        profesor.setCorreoInstitucional(correoLimpio);
        profesor.setContrasena(pendiente.getContrasenaHash());
        profesor.setTipo(ServiceLocator.getTipoProfesorDAO().buscarPorId(pendiente.getIdTipo()));
        if (!ServiceLocator.getProfesorDAO().guardar(profesor)) {
            throw new IllegalArgumentException("No se pudo crear la cuenta. Intenta más tarde.");
        }
        dao.eliminar(correoLimpio);
    }

    public void reenviarCodigo(String correo) {
        String correoLimpio = normalizar(correo);
        RegistroPendiente pendiente = ServiceLocator.getRegistroPendienteDAO().buscarPorCorreo(correoLimpio);
        if (pendiente == null) {
            throw new IllegalArgumentException("No encontramos tu solicitud. Vuelve a registrarte");
        }
        if (pendiente.getEnvios() >= MAX_ENVIOS) {
            throw new IllegalArgumentException("Ya no puedes reenviar el código. Vuelve a registrarte");
        }
        long espera = Duration.between(LocalDateTime.now(),
                pendiente.getUltimoEnvio().plusSeconds(ESPERA_SEGUNDOS)).getSeconds();
        if (espera > 0) {
            throw new IllegalArgumentException("Espera " + espera + " segundos para reenviar el código");
        }

        String codigo = generarCodigo();
        try {
            ServicioCorreo.enviarCodigo(correoLimpio, codigo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No se pudo enviar el código. Intenta más tarde.");
        }
        pendiente.setCodigoHash(BCrypt.hashpw(codigo, BCrypt.gensalt(10)));
        pendiente.setVence(LocalDateTime.now().plusMinutes(MINUTOS_VIGENCIA));
        pendiente.setIntentos(0);
        pendiente.setEnvios(pendiente.getEnvios() + 1);
        pendiente.setUltimoEnvio(LocalDateTime.now());
    }

    private void validarDatos(String correo, String contrasena, String confirmacion, Integer idTipo) {
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isEmpty()
                || confirmacion == null || confirmacion.isEmpty() || idTipo == null) {
            throw new IllegalArgumentException("Completa todos los campos");
        }
        String correoLimpio = normalizar(correo);
        if (correoLimpio.length() > 60) {
            throw new IllegalArgumentException("El correo no puede tener más de 60 caracteres");
        }
        if (!CORREO_UABC.matcher(correoLimpio).matches()) {
            throw new IllegalArgumentException("Usa tu correo institucional, que termina en " + DOMINIO);
        }
        if (contrasena.length() < 8 || contrasena.length() > 60) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 60 caracteres");
        }
        if (!contrasena.equals(confirmacion)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    }

    private String generarCodigo() {
        return String.format("%06d", random.nextInt(1_000_000));
    }

    private String normalizar(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase();
    }
}
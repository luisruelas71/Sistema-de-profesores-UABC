package mx.desarrollo.facade;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 esto aun esta en pruebas xd
 */
public class ServicioCorreo {

    private ServicioCorreo() {
    }

    public static void enviarCodigo(String destino, String codigo) throws MessagingException {
        String host = System.getenv("SMTP_HOST");
        if (host == null || host.isBlank()) {
            System.out.println("[MODO PRUEBA] SMTP sin configurar. Código de verificación para "
                    + destino + ": " + codigo);
            return;
        }

        final String usuario = System.getenv("SMTP_USER");
        final String clave = System.getenv("SMTP_PASSWORD");
        String puerto = System.getenv("SMTP_PORT") == null ? "587" : System.getenv("SMTP_PORT");
        String remitente = System.getenv("SMTP_FROM") == null ? usuario : System.getenv("SMTP_FROM");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto);

        Session sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        MimeMessage mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        mensaje.setSubject("Código de verificación - Sistema de Profesores UABC", "UTF-8");
        mensaje.setText("Tu código de verificación es: " + codigo
                + "\n\nVence en 10 minutos. Si no solicitaste esta cuenta, ignora este mensaje.", "UTF-8");
        Transport.send(mensaje);
    }
}
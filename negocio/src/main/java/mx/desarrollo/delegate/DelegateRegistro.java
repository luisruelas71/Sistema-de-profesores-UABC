package mx.desarrollo.delegate;

import java.util.List;
import mx.desarrollo.entity.TipoProfesor;
import mx.desarrollo.integration.ServiceFacadeLocator;

public class DelegateRegistro {

    public List<TipoProfesor> listarTipos() {
        return ServiceFacadeLocator.getFacadeRegistro().listarTipos();
    }

    public void solicitarRegistro(String correo, String contrasena, String confirmacion, Integer idTipo) {
        ServiceFacadeLocator.getFacadeRegistro().solicitarRegistro(correo, contrasena, confirmacion, idTipo);
    }

    public void verificarCodigo(String correo, String codigo) {
        ServiceFacadeLocator.getFacadeRegistro().verificarCodigo(correo, codigo);
    }

    public void reenviarCodigo(String correo) {
        ServiceFacadeLocator.getFacadeRegistro().reenviarCodigo(correo);
    }
}
package mx.desarrollo.delegate;

import mx.desarrollo.entity.Firma;
import mx.desarrollo.integration.ServiceFacadeLocator;

public class DelegateFirma {

    public boolean registrarFirma(Firma firma) {
        return ServiceFacadeLocator.getFacadeFirma().registrarFirma(firma);
    }
}
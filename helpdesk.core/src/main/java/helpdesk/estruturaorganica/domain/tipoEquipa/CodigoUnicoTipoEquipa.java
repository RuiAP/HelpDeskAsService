package helpdesk.estruturaorganica.domain.tipoEquipa;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class CodigoUnicoTipoEquipa implements ValueObject, Comparable<CodigoUnicoTipoEquipa> {

    private String codigoUnico;

    protected CodigoUnicoTipoEquipa(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    protected CodigoUnicoTipoEquipa() {
        // for ORM
    }

    @Override
    public String toString() {
        return codigoUnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CodigoUnicoTipoEquipa codigoUnico1 = (CodigoUnicoTipoEquipa) o;
        return Objects.equals(codigoUnico, codigoUnico1.codigoUnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoUnico);
    }

    @Override
    public int compareTo(CodigoUnicoTipoEquipa o) {
        return codigoUnico.compareTo(o.codigoUnico);
    }  
    
}

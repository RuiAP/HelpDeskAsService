package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.naming.ldap.PagedResultsControl;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Identificador implements ValueObject, Comparable<Identificador> {


    private String identificador;

    protected Identificador(String identificador) {
        Preconditions.nonEmpty(identificador, "Identificador não pode ser vazio");
        this.identificador = identificador;
    }

    protected Identificador() {
    }

    public static Identificador valueOf(String identificador){
        return new Identificador(identificador);
    }

      @Override
    public String toString() {
        return identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        helpdesk.especificacaoservico.domain.catalogo.Identificador codigo1 = (helpdesk.especificacaoservico.domain.catalogo.Identificador) o;
        return Objects.equals(identificador, codigo1.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public int compareTo(helpdesk.especificacaoservico.domain.catalogo.Identificador o) {
        return identificador.compareTo(o.identificador);
    }

}

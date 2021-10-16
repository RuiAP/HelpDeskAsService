package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Identificador implements ValueObject, Comparable<Identificador> {


    String identificador;

    public Identificador(String codigo) {
        this.identificador = codigo;
    }

    public Identificador() {
        // for ORM
    }

    @Override
    public String toString() {
        return identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identificador codigo1 = (Identificador) o;
        return Objects.equals(identificador, codigo1.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public int compareTo(Identificador o) {
        return identificador.compareTo(o.identificador);
    }
}

package helpdesk.solicitacaoservico.domain.pedido;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class IdentificadorPedido implements ValueObject, Comparable<IdentificadorPedido> {

    private String identificador;

    public IdentificadorPedido(String identificador) {
        this.identificador = identificador;
    }

    protected IdentificadorPedido() {
        // for ORM
    }

    public static IdentificadorPedido valueOf(String identificador) {
        return new IdentificadorPedido(identificador);
    }

    @Override
    public String toString() {
        return identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentificadorPedido ident1 = (IdentificadorPedido) o;
        return Objects.equals(identificador, ident1.identificador);

    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public int compareTo(IdentificadorPedido o) {
        return identificador.compareTo(o.identificador);
    }

}
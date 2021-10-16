package helpdesk.nivelservico.domain.nivelcriticidade;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Objects;

@Embeddable
public class Designacao implements ValueObject, Comparable<Designacao> {
    
    String designacao;

    public Designacao(String designacao) {
        this.designacao =designacao;
    }

    protected Designacao() {
        //for ORM
    }

    public static Designacao valueOf(String designacao) {
        return new Designacao(designacao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designacao that = (Designacao) o;
        return Objects.equals(designacao, that.designacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designacao);
    }

    @Override
    public String toString() {
        return designacao;
    }

    @Override
    public int compareTo(Designacao o) {
        return designacao.compareTo(o.designacao);
    }
}

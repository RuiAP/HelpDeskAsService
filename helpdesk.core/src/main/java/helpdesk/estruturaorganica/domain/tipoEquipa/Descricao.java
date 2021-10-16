package helpdesk.estruturaorganica.domain.tipoEquipa;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class Descricao implements ValueObject, Comparable<Descricao> {

    String descricao;

    public Descricao(String descricao) {
        alterarDesignacao(descricao);
    }

    public Descricao() {
        //for ORM
    }

    public void alterarDesignacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Descricao descricao1 = (Descricao) o;
        return Objects.equals(descricao, descricao1.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao);
    }

    @Override
    public int compareTo(Descricao o) {
        return descricao.compareTo(o.descricao);
    }

}

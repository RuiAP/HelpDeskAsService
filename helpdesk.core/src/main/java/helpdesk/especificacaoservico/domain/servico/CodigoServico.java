package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CodigoServico implements ValueObject, Comparable<CodigoServico> {

    private String codigoServico;

    protected CodigoServico(String codigo) {
        Preconditions.nonNull("Código do Serviço não pode ser vazio ou nulo");
        this.codigoServico = codigo;
    }

    protected CodigoServico() {
        // for ORM
    }

    public static CodigoServico valueOf(String codigoServico){
        return new CodigoServico(codigoServico);
    }

    @Override
    public String toString() {
        return codigoServico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodigoServico codigo1 = (CodigoServico) o;
        return Objects.equals(codigoServico, codigo1.codigoServico);

    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoServico);
    }

    @Override
    public int compareTo(CodigoServico o) {
        return codigoServico.compareTo(o.codigoServico);
    }

}

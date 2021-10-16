package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class DescricaoCompleta implements ValueObject {

    private String descricaoCompleta;

    public DescricaoCompleta(String descricaoCompleta) {

        Preconditions.nonEmpty(descricaoCompleta, "Descrição Completa não pode ser vazia.");
        this.descricaoCompleta = descricaoCompleta;
    }

    public DescricaoCompleta() {
    }

    @Override
    public String toString() {
        return descricaoCompleta;
    }
}

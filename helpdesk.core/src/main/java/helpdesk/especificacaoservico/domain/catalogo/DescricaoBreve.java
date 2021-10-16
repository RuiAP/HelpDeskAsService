package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class DescricaoBreve implements ValueObject {

    private String descricaoBreve;

    public DescricaoBreve(String descricaoBreve) {
        Preconditions.nonEmpty(descricaoBreve, "Descrição Breve não pode ser vazia.");
        this.descricaoBreve = descricaoBreve;
    }

    public DescricaoBreve() {
    }

    @Override
    public String toString() {
        return descricaoBreve;
    }
}

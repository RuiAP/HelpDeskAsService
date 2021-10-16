package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DescricaoBreve implements ValueObject {

    private String descricaoBreve;

    public DescricaoBreve(String descricaoBreve) {
        this.descricaoBreve = descricaoBreve;
    }

    protected DescricaoBreve() {
        //for ORM
    }

    @Override
    public String toString() {
        return descricaoBreve;
    }
}

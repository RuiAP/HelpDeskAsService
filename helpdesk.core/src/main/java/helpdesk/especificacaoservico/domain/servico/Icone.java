package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Icone implements ValueObject {

    private String icone;

    public Icone(String icone) {
        this.icone = icone;
    }

    public Icone() {
        //for ORM
    }

    @Override
    public String toString() {
        return icone;
    }
}

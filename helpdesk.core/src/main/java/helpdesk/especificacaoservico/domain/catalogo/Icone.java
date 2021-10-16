package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Icone implements ValueObject {

    private String icone;

    public Icone(String icone) {
        this.icone=icone;
    }

    public Icone() {
        //for ORM
    }
}

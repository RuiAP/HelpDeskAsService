package helpdesk.nivelservico.domain.nivelcriticidade;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Objetivo implements ValueObject {

    String descricao;

    public Objetivo(String descricao) {
        this.descricao =descricao;
    }

    protected Objetivo() {
        //for ORM
    }

    public static Objetivo valueOf(String titulo) {
        return new Objetivo(titulo);
    }

    @Override
    public String toString() {
        return descricao;
    }

}

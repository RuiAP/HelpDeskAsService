package helpdesk.nivelservico.domain.nivelcriticidade;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Escala implements ValueObject {

    int value;

    public Escala(int value) {
        this.value=value;
    }

    protected Escala() {
        //for ORM
    }

    public static Escala valueOf(int value) {
        return new Escala(value);
    }

    @Override
    public String toString() {
        return value + "";
    }

}

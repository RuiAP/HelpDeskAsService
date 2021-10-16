package helpdesk.nivelservico.domain.nivelcriticidade;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Etiqueta implements ValueObject {

    String titulo;

    public Etiqueta(String titulo) {
        this.titulo=titulo;
    }

    protected Etiqueta() {
        //for ORM
    }

    public static Etiqueta valueOf(String titulo) {
        return new Etiqueta(titulo);
    }

    @Override
    public String toString() {
        return titulo;
    }

}

package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Titulo implements ValueObject {

    private String titulo;

    public Titulo(String titulo) {
        this.titulo=titulo;
    }

    protected Titulo() {
        //for ORM
    }

    public static Titulo valueOf(String titulo) {
        return new Titulo(titulo);
    }

    @Override
    public String toString() {
        return titulo;
    }

}

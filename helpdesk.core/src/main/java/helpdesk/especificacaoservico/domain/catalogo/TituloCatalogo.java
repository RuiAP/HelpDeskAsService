package helpdesk.especificacaoservico.domain.catalogo;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class TituloCatalogo implements ValueObject {


    private String titulo;

    public TituloCatalogo(String titulo) {
        this.titulo = titulo;
    }

    public TituloCatalogo() {
    }


    @Override
    public String toString() {
        return titulo;
    }
}

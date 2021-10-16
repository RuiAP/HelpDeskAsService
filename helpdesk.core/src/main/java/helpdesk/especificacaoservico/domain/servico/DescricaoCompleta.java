package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DescricaoCompleta implements ValueObject {

    private String descricaoCompleta;

    public DescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

    public DescricaoCompleta() {
        //for ORM
    }

    @Override
    public String toString() {
        return descricaoCompleta;
    }
}

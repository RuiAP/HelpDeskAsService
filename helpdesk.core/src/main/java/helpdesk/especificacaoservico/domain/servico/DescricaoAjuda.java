package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DescricaoAjuda implements ValueObject {

    String descricao;

    public DescricaoAjuda(String descricao) {
        this.descricao = descricao;
    }

    public DescricaoAjuda() {
        //for ORM
    }

    @Override
    public String toString() {
        return  descricao;
    }
}

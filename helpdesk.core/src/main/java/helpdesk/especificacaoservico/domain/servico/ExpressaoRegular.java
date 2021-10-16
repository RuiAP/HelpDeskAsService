package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class ExpressaoRegular implements ValueObject {

    String expressao;

    public ExpressaoRegular(String expressao) {
        this.expressao = expressao;
    }

    protected ExpressaoRegular() {
        //for ORM
    }

    @Override
    public String toString() {
        return expressao;
    }
}

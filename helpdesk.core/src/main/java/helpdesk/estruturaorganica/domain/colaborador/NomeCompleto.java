package helpdesk.estruturaorganica.domain.colaborador;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Embeddable;


@Embeddable
public class NomeCompleto implements ValueObject {

    private String nomeCompleto;
    
    public NomeCompleto(String nome) {
        nomeCompleto = nome;
    }

    protected NomeCompleto() {

    }

    @Override
    public String toString() {
        return nomeCompleto;
    }
}

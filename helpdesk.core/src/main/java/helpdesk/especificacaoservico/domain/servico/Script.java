package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;

@Embeddable
public class Script implements ValueObject {

    @Lob
    @Basic
    private String script;

    public Script(String script) {
        this.script = script;
    }

    protected Script() {
    }


    public String value() {
        return this.script;
    }
}

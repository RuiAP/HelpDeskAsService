package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class EtiquetaApresentacao implements ValueObject {

    String etiqueta;

    public EtiquetaApresentacao(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public EtiquetaApresentacao() {
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}

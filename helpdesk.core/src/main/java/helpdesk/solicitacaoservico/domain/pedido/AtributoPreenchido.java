package helpdesk.solicitacaoservico.domain.pedido;

import eapli.framework.domain.model.ValueObject;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import javax.persistence.*;
import java.util.Date;

/**
 * Servico
 * <p>
 * Esta classe representa um serviço. Implementa Domain Driven Design onde
 * esta classe é a própria base do seu agregado.
 */

@Entity
public class AtributoPreenchido implements ValueObject {

    @Id
    @GeneratedValue
    Long pk;

    @Column
    String value;

    @Column
    EtiquetaApresentacao etiquetaApresentacao;

    @Column
    NomeVariavel nomeVariavel;

    @Column
    TipoDadosBase tipoDadosBase;

    public AtributoPreenchido(NomeVariavel nomeVariavel,EtiquetaApresentacao etiquetaApresentacao, TipoDadosBase tipoDadosBase, String value) {

        if (value == null || etiquetaApresentacao == null || tipoDadosBase == null || nomeVariavel == null)
            throw new IllegalArgumentException();

        this.nomeVariavel = nomeVariavel;
        this.etiquetaApresentacao = etiquetaApresentacao;
        this.tipoDadosBase = tipoDadosBase;
        this.value = value;
    }

    protected AtributoPreenchido() {
        //for ORM
    }

    public NomeVariavel getNomeVariavel(){
        return this.nomeVariavel;
    }
    public EtiquetaApresentacao getEtiquetaApresentacao() {
        return etiquetaApresentacao;
    }

    public String getValue() {
        return value;
    }

    public TipoDadosBase getTipoDadosBase() {
        return tipoDadosBase;
    }
}

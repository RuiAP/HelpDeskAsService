package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Set;

/**
 * Servico
 * <p>
 * Esta classe representa um serviço. Implementa Domain Driven Design onde
 * esta classe é a própria base do seu agregado.
 */

@Entity
public class AtributoFormulario implements ValueObject {

    @Id
    @GeneratedValue // (strategy = GenerationType.AUTO)
    private Long pk;

    @Column
    private NomeVariavel nomeVariavel;

    @Column
    private EtiquetaApresentacao etiquetaApresentacao;

    @Column
    private DescricaoAjuda descricaoAjuda;

    @Column
    private TipoDadosBase tipoDadosBase;

    @Column
    private ExpressaoRegular expressaoRegular;

    @Column
    private boolean preenchimentoObrigatorio;

    @ElementCollection
    private Set<String> optionValues;

    public AtributoFormulario(String nomeVariavel, String etiquetaApresentacao,
                              String descricaoAjuda, TipoDadosBase tipoDadosBase,
                              String expressaoRegular, boolean preenchimentoObrigatorio) {

        Preconditions.noneNull(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase, expressaoRegular);

        this.nomeVariavel = new NomeVariavel(nomeVariavel);
        this.etiquetaApresentacao = new EtiquetaApresentacao(etiquetaApresentacao);
        this.descricaoAjuda = new DescricaoAjuda(descricaoAjuda);
        this.tipoDadosBase = tipoDadosBase;
        this.expressaoRegular = new ExpressaoRegular(expressaoRegular);
        this.preenchimentoObrigatorio = preenchimentoObrigatorio;
    }

    public AtributoFormulario(String nomeVariavel, String etiquetaApresentacao,
                              String descricaoAjuda, TipoDadosBase tipoDadosBase,
                              String expressaoRegular, boolean preenchimentoObrigatorio, Set<String> optionValues) {

        this(nomeVariavel, etiquetaApresentacao, descricaoAjuda, tipoDadosBase,
                expressaoRegular, preenchimentoObrigatorio);
        this.optionValues = optionValues;
    }

    protected AtributoFormulario() {
        //for ORM
    }

    public boolean isPreenchimentoObrigatorio(){
        return  preenchimentoObrigatorio;
    }

    public NomeVariavel getNomeVariavel() {
        return nomeVariavel;
    }

    public EtiquetaApresentacao getEtiquetaApresentacao() {
        return etiquetaApresentacao;
    }

    public TipoDadosBase getTipoDadosBase() {
        return tipoDadosBase;
    }

    public String getDescricaoAjuda() {
        return this.descricaoAjuda.toString();
    }

    public Set<String> getOptionValues() {
        return optionValues;
    }

    public ExpressaoRegular getExpressaoRegular() {
        return expressaoRegular;
    }
}

package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;

@Entity
public class Atividade implements ValueObject {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private Script script;

    @OneToOne(cascade = CascadeType.ALL)
    private Formulario formularioAtividade;

    @Enumerated(EnumType.STRING)
    private ResponsavelAprovacao responsavelPorAprovacao;

    @Embedded
    private ResponsavelRealizacao reponsavelPorRealizacao;

    public Atividade(String script) {
        this.script = new Script(script);
    }

    public Atividade(ResponsavelAprovacao responsavelPorAprovacao, Formulario formularioAtividade) {
        this.formularioAtividade = formularioAtividade;
        this.responsavelPorAprovacao = responsavelPorAprovacao;
    }

    public Atividade(ResponsavelRealizacao reponsavelPorRealizacao, Formulario formularioAtividade) {
        this.formularioAtividade = formularioAtividade;
        this.reponsavelPorRealizacao = reponsavelPorRealizacao;
    }

    protected Atividade() {
    }


    public Formulario getFormularioAtividade() {
        return formularioAtividade;
    }

    public Script getScript() {
        return script;
    }

    public ResponsavelAprovacao getResponsavelPorAprovacao() {
        return responsavelPorAprovacao;
    }

    public ResponsavelRealizacao getReponsavelPorRealizacao() {
        return reponsavelPorRealizacao;
    }

    public String script() {
        return this.script.value();
    }

    public boolean isAtividadeAutomatica(){
        return script!=null;
    }
}

package helpdesk.especificacaoservico.domain.servico;

import eapli.framework.domain.model.ValueObject;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import javax.persistence.*;

@Embeddable
public class ResponsavelRealizacao implements ValueObject {


    @OneToOne (optional = true)
    private Colaborador colaboradorResponsavel;

    @OneToOne (optional = true)
    private Equipa equipaResponsavel;

    protected ResponsavelRealizacao() {
        //for ORM
    }

    public ResponsavelRealizacao(Equipa equipaResponsavel) {
        this.equipaResponsavel = equipaResponsavel;
    }

    public ResponsavelRealizacao(Colaborador colaboradorResponsavel) {
        this.colaboradorResponsavel = colaboradorResponsavel;
    }


    public boolean temColaboradorAssignado(){
        return colaboradorResponsavel != null;
    }

    public boolean foiAssignadaEquipa(){
        return equipaResponsavel != null;
    }

    protected Colaborador colaborador(){
        return this.colaboradorResponsavel;
    }

    public Equipa equipa(){
        return this.equipaResponsavel;
    }

    public Colaborador getColaboradorResponsavel() {
        return colaboradorResponsavel;
    }

    public Equipa getEquipaResponsavel() {
        return equipaResponsavel;
    }
}

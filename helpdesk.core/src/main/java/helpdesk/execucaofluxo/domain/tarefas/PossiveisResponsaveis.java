package helpdesk.execucaofluxo.domain.tarefas;

import eapli.framework.domain.model.ValueObject;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Embeddable
public class PossiveisResponsaveis implements ValueObject {


    @ManyToMany
    private Set<Colaborador> colaboradoresPossiveis = new HashSet<>();

    @OneToOne
    private Equipa equipaPossivel;

    protected PossiveisResponsaveis() {
        //for ORM
    }

    protected PossiveisResponsaveis(Set<Colaborador> colaboradoresPossiveis){
        this.colaboradoresPossiveis = colaboradoresPossiveis;
    }

    protected PossiveisResponsaveis(Equipa equipa){
        this.equipaPossivel = equipa;
    }

    protected Set<Colaborador> getColaboradoresPossiveis(){
        return new HashSet<>(this.colaboradoresPossiveis);
    }

    public Equipa getEquipaPossivel() {
        return this.equipaPossivel;
    }

    public boolean assignadaAEquipa(){
        return this.equipaPossivel != null;
    }
}

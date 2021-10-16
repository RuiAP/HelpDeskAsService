package helpdesk.execucaofluxo.domain.tarefas;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class PrioridadeTarefa implements ValueObject {

    int prioridade;

    protected PrioridadeTarefa(){

    }

    protected PrioridadeTarefa(int prioridade){
        this.prioridade = prioridade;
    }

    public int value(){
        return this.prioridade;
    }

    @Override
    public String toString() {
        return "" +prioridade;
    }
}

package helpdesk.execucaofluxo.domain.tarefas;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AbstractTarefaCriadaEvent extends DomainEventBase implements DomainEvent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long eventId;

    @ManyToOne
    TarefaAutomatica tarefaCriada;

    public AbstractTarefaCriadaEvent() {
    }

    public AbstractTarefaCriadaEvent(TarefaAutomatica tarefaCriada) {
        this.tarefaCriada = tarefaCriada;
    }

    public TarefaAutomatica tarefa(){
        return this.tarefaCriada;
    }
}

package helpdesk.motorFluxos.presentation;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.validations.Preconditions;
import helpdesk.execucaofluxo.application.tarefas.RealizarTarefaAutomaticaController;
import helpdesk.execucaofluxo.domain.tarefas.TarefaAutoCriadaEvent;

public class TarefaAutomaticaWatchDog implements EventHandler {
    @Override
    public void onEvent(DomainEvent domainEvent) {
        Preconditions.ensure(domainEvent instanceof TarefaAutoCriadaEvent);

        final TarefaAutoCriadaEvent event = (TarefaAutoCriadaEvent) domainEvent;

       RealizarTarefaAutomaticaController.getInstance().realizarTarefaAutomatica(event.tarefa());
    }
}

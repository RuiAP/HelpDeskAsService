package helpdesk.execucaofluxo.domain.tarefas;

public class TarefaAutoCriadaEvent extends AbstractTarefaCriadaEvent{

    private static final long serialVersionUID = 1L;

    public TarefaAutoCriadaEvent() {
    }

    public TarefaAutoCriadaEvent(TarefaAutomatica tarefaCriada) {
        super(tarefaCriada);
    }
}

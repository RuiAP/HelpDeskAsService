package helpdesk.execucaofluxo.application.tarefas.assignacao;

import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;

public class AssignacaoTarefaPorCarga implements AlgoritmoAssignacao{

    @Override
    public TarefaManual assignarTarefa(TarefaManual tarefa) {
        return tarefa;
    }
}

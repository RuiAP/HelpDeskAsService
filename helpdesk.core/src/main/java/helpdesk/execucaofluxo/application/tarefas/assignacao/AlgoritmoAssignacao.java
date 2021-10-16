package helpdesk.execucaofluxo.application.tarefas.assignacao;

import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;

public interface AlgoritmoAssignacao {

    TarefaManual assignarTarefa(TarefaManual tarefa);
}

package helpdesk.execucaofluxo.application.tarefas.assignacao;

import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;

public class AssignacaoTarefaPorReivindicacao implements AlgoritmoAssignacao{
    @Override
    public TarefaManual assignarTarefa(TarefaManual tarefa) {
        //não é realizada nenhuma assignação automática.
        //A tarefa é deixada por assignar para ser possivel o Colaborador Reivindicar a Tarefa
        return tarefa;
    }
}

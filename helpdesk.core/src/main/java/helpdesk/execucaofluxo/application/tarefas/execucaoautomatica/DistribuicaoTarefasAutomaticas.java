package helpdesk.execucaofluxo.application.tarefas.execucaoautomatica;

import helpdesk.execucaofluxo.domain.tarefas.TarefaAutomatica;

import java.util.List;
import java.util.Map;

public interface DistribuicaoTarefasAutomaticas {

    int assignarInstanciaExecutor(int lastAssignedPort,  Map<Integer, List<TarefaAutomatica>> tarefasEmExecucao);

}

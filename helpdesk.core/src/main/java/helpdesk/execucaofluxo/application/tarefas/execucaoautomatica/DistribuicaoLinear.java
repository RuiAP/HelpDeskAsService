package helpdesk.execucaofluxo.application.tarefas.execucaoautomatica;

import helpdesk.execucaofluxo.domain.tarefas.TarefaAutomatica;

import java.util.List;
import java.util.Map;

public class DistribuicaoLinear implements DistribuicaoTarefasAutomaticas {
    @Override
    public int assignarInstanciaExecutor(int lastAssignedPort, Map<Integer, List<TarefaAutomatica>> tarefasEmExecucao) {
        return lastAssignedPort + 1 < tarefasEmExecucao.size() ? lastAssignedPort + 1 : 0;
    }
}

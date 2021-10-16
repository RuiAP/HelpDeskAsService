package helpdesk.execucaofluxo.application.tarefas.execucaoautomatica;

import helpdesk.execucaofluxo.domain.tarefas.TarefaAutomatica;

import java.util.List;
import java.util.Map;

public class DistribuicaoPorCarga implements DistribuicaoTarefasAutomaticas {
    @Override
    public int assignarInstanciaExecutor(int lastAssignedPort, Map<Integer, List<TarefaAutomatica>> tarefasEmExecucao) {
        int selectedPort = 0;
        int max = Integer.MAX_VALUE;
        for(Map.Entry<Integer, List<TarefaAutomatica>> entry : tarefasEmExecucao.entrySet()){
            if(entry.getValue().size()<max){
                max = entry.getValue().size();
                selectedPort = entry.getKey().intValue();
            }
        }
        return selectedPort;
    }
}

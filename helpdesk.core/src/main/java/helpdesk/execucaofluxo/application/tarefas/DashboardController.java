package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.execucaofluxo.domain.tarefas.Tarefa;

public class DashboardController {

    private final ListarTarefaService tarefaService = new ListarTarefaService();


    public DashboardController(){

    }



    public Iterable<Tarefa> tarefasDoColaborador(String userId) {
        return tarefaService.tarefasDoColaborador(userId);
    }



}

package helpdesk.execucaofluxo.application.tarefas.assignacao;

import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;

public class AssignacaoTarefaService {

    private final TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();
    private final ColaboradorRepository colaboradorRepository = PersistenceContext.repositories().colaboradores();

    private final AlgoritmoAssignacao algoritmoAssignacao = SeletorDeAlgoritmos.loadFromProperties();


    public AssignacaoTarefaService() {
    }


    public boolean assignarTarefa(TarefaManual tarefa) {
        tarefa = algoritmoAssignacao.assignarTarefa(tarefa);
        if(tarefa == null){
            return false;
        }else{
            tarefaRepository.save(tarefa);
            return true;
        }

    }
}

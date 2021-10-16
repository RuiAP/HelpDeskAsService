package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;


public class ReivindicarTarefaController {

    private final ListarTarefaService listarTarefaService = new ListarTarefaService();
    private final TarefaRepository tarefaRepo = PersistenceContext.repositories().tarefas();
    private final ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    private final Colaborador colaboradorAtual = listarColaboradorService.colaboradorAtual();

    public ReivindicarTarefaController() {
    }

    public TarefaManual reivindicarTarefa(TarefaManual tarefa){
        tarefa.assignarTarefa(colaboradorAtual);
        return tarefaRepo.save(tarefa);
    }

    public Iterable<Tarefa> tarefaDisponiveisParaReivindicar(){
        return listarTarefaService.tarefaDisponiveisParaReivindicar(colaboradorAtual.identity().toString());

    }






}

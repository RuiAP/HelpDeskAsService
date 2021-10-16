package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.execucaofluxo.domain.tarefas.CodigoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.EstadoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;

import java.util.Optional;

public class ListarTarefaService {


    private final TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();

    public ListarTarefaService() {
    }


    public Iterable<Tarefa> todasTarefas(){
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> tarefaByCodigoTarefa(String codigoTarefa){
        return tarefaRepository.ofIdentity(CodigoTarefa.valueOf(Integer.parseInt(codigoTarefa)));
    }

    public Iterable<Tarefa> tarefasDoColaborador(String nrMecanografico){
        return tarefaRepository.todasTarefasDoColaborador(NrMecanografico.valueOf(nrMecanografico));
    }


    public Iterable<Tarefa> tarefasNaoConcluidasByColaborador(String nrMecanografico){
        return tarefaRepository.tarefasPorEstadoEColaboradorResponsavel(
                NrMecanografico.valueOf(nrMecanografico), EstadoTarefa.ASSIGNADA);
    }

    public Iterable<Tarefa> tarefasConcluidasByColaborador(String nrMecanografico){
        return tarefaRepository.tarefasPorEstadoEColaboradorResponsavel(
                NrMecanografico.valueOf(nrMecanografico), EstadoTarefa.CONCLUIDA);
    }

    public Iterable<Tarefa> tarefaDisponiveisParaReivindicar(String nrMecanografico){
        return tarefaRepository.tarefaDisponiveisParaReivindicar(NrMecanografico.valueOf(nrMecanografico));

    }
}

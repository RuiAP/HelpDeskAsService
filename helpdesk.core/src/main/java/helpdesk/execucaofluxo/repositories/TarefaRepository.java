package helpdesk.execucaofluxo.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.execucaofluxo.domain.tarefas.CodigoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.EstadoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;

import java.util.Optional;

public interface TarefaRepository extends DomainRepository<CodigoTarefa, Tarefa> {


    Iterable<Tarefa> todasTarefasDoColaborador(NrMecanografico nrMecanografico);


    Iterable<Tarefa> tarefasPorEstadoEColaboradorResponsavel(NrMecanografico nrMecanografico, EstadoTarefa estado);

    Iterable<Tarefa> tarefaDisponiveisParaReivindicar(NrMecanografico nrMecanografico);

    Optional<TarefaManual> tarefaAprovacaoPorPedido(IdentificadorPedido identificadorPedido);
}

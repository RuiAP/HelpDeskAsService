package persistence.impl.jpa;

import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.execucaofluxo.domain.tarefas.EstadoTarefa;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class JpaTarefaRepositoryTest {

//    JpaTarefaRepository repo = (JpaTarefaRepository) PersistenceContext.repositories().tarefas();

    @Before
    public void setUp() {
    }

    @Test
    public void tarefasByColaborador() {
//        Iterable<Tarefa> tarefas = repo.todasTarefasDoColaborador(NrMecanografico.valueOf("1002"));
//        tarefas.forEach(t -> System.out.println(t.identity()));
    }

    @Test
    public void tarefasPorEstadoEColaborador() {
//        Iterable<Tarefa> tarefas = repo.tarefasPorEstadoEColaboradorResponsavel(NrMecanografico.valueOf("1002"), EstadoTarefa.PENDENTE);
//        tarefas.forEach(t -> System.out.println(t.identity()));
    }

    @Test
    public void tarefaDisponiveisParaReivindicar() {
//        Iterable<Tarefa> tarefas = repo.tarefaDisponiveisParaReivindicar(NrMecanografico.valueOf("1001"));
//        tarefas.forEach(t -> System.out.println(t.identity()));
    }


    @Test
    public void tarefaAprovacaoPorPedido() {
//        Optional<TarefaManual> tarefa = repo.tarefaAprovacaoPorPedido(IdentificadorPedido.valueOf("2021/00001"));
//        System.out.println(tarefa.isPresent());
//        tarefa.ifPresent(System.out::println);
    }
}

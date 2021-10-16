package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.Application;
import helpdesk.execucaofluxo.application.tarefas.execucaoautomatica.DistribuicaoTarefasAutomaticas;
import helpdesk.execucaofluxo.application.tarefas.execucaoautomatica.SeletorDeAlgoritmos;
import helpdesk.execucaofluxo.domain.tarefas.TarefaAutomatica;
import helpdesk.execucaofluxo.domain.tarefas.TarefaManual;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;
import helpdesk.protocoloComunicacao.clients.ExecutorTarefasClient;
import helpdesk.solicitacaoservico.domain.pedido.AtributoPreenchido;

import java.util.*;


public class RealizarTarefaAutomaticaController {

    private static RealizarTarefaAutomaticaController instance;

    private final DistribuicaoTarefasAutomaticas algoritmoExecucao = SeletorDeAlgoritmos.loadFromProperties();

    private int lastPort;
    private Map<Integer, List<TarefaAutomatica>> tarefasEmExecucao;

    private static final Object lock = new Object();

    public static RealizarTarefaAutomaticaController getInstance() {
        synchronized (lock) {
            if (instance == null)
                instance = new RealizarTarefaAutomaticaController();

        }
        return instance;
    }

    private RealizarTarefaAutomaticaController() {
        tarefasEmExecucao = new HashMap<>();
        String ports[] = (Application.settings().getProperty("executorTarefas.portNumber")).split(";");
        for (int i = 0; i < ports.length; i++) {
            tarefasEmExecucao.put((Integer) i, new ArrayList<>());
        }
    }

    public void realizarTarefaAutomatica(TarefaAutomatica tarefaAutomatica) {


        int portNumber = algoritmoExecucao.assignarInstanciaExecutor(lastPort, tarefasEmExecucao);
        lastPort = portNumber;

        Thread clientThread = new Thread(new ConexaoExecutor(tarefasEmExecucao.get(portNumber), tarefaAutomatica, portNumber));
        clientThread.setDaemon(true);
        clientThread.start();
    }


    static class ConexaoExecutor implements Runnable {

        List<TarefaAutomatica> tarefasEmExecucao;
        int portNumber;
        TarefaAutomatica tarefa;
        ExecutorTarefasClient executorTarefasClient;
        TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();

        public ConexaoExecutor(List<TarefaAutomatica> tarefas, TarefaAutomatica tarefaAutomatica, int portNumber) {
            tarefasEmExecucao = tarefas;
            tarefasEmExecucao.add(tarefaAutomatica);
            this.portNumber = portNumber;
            this.tarefa = tarefaAutomatica;
            this.executorTarefasClient = new ExecutorTarefasClient();
        }

        @Override
        public void run() {
            boolean resultado;

            tarefa = (TarefaAutomatica) tarefaRepository.ofIdentity(tarefa.identity()).get();

            //recolher todos os atributos que foram preenchidos durante a solicitação e durante a aprovação
            // pois podem ser necessários para a execução do script de realização
            Optional<TarefaManual> tarefaAprovacao = tarefaRepository.tarefaAprovacaoPorPedido(tarefa.getPedido().identity());
            List<AtributoPreenchido> atributosPreenchidos = new LinkedList<>();
            tarefaAprovacao.ifPresent(
                    tarefaManual -> atributosPreenchidos.addAll(tarefaManual.atributosPreenchidos()));

            atributosPreenchidos.addAll(tarefa.getPedido().atributosPreenchidosNaSolicitacao());

            executorTarefasClient.getConnection(portNumber);
            resultado = executorTarefasClient.realizarTarefaAutomatica(tarefa, atributosPreenchidos);
            executorTarefasClient.closeConnection();

            tarefasEmExecucao.remove(tarefa);

            tarefa.darPorRealizada(resultado);

            tarefa = tarefaRepository.save(tarefa);
            new AvancarFluxoController().tarefaRealizada(tarefa);
        }


    }

}




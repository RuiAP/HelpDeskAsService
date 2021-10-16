package helpdesk.execucaofluxo.application.tarefas;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;
import helpdesk.execucaofluxo.application.tarefas.assignacao.AssignacaoTarefaService;
import helpdesk.execucaofluxo.domain.tarefas.*;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AvancarFluxoService {

    private static final Logger LOGGER = LogManager.getLogger(AvancarFluxoService.class);


    private final TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();
    private final PedidoRepository pedidoRepository = PersistenceContext.repositories().pedidos();

    private final EventPublisher dispatcher = InProcessPubSub.publisher();
    private final AssignacaoTarefaService assignacaoTarefaService = new AssignacaoTarefaService();



    public boolean novoPedidoSubmetido(Pedido pedido) {
        if(pedido.estado() != EstadoPedido.SUBMETIDO){
            return false;
        }

        if (pedido.getServico().necessitaAprovacao()) {
            pedido.iniciarAprovacao();
            Pedido p = pedidoRepository.save(pedido);
            TarefaManual t = criarTarefaDeAprovacao(p);
            t = tarefaRepository.save(t);
            return assignacaoTarefaService.assignarTarefa(t);

        } else {
            pedido.iniciarResolucao();
            Pedido p = pedidoRepository.save(pedido);
            Tarefa t = criarTarefaDeRealizacao(p);
            t = tarefaRepository.save(t);
            if (t instanceof TarefaAutomatica) {
                DomainEvent event = new TarefaAutoCriadaEvent((TarefaAutomatica) t);
                dispatcher.publish(event);
            } else {
                return assignacaoTarefaService.assignarTarefa((TarefaManual) t);
            }
        }
        return true;

    }

    public boolean tarefaConcluida(Tarefa tarefaConcluida){
        tarefaConcluida = PersistenceContext.repositories().tarefas().ofIdentity(tarefaConcluida.identity()).get();
        Pedido pedido = tarefaConcluida.getPedido();

        try{

            switch (pedido.estado()) {

                case EM_APROVACAO:
                    if(tarefaConcluida.concluidaComSucesso()){
                        pedido.iniciarResolucao();
                        Pedido p = pedidoRepository.save(pedido);
                        Tarefa t = criarTarefaDeRealizacao(p);
                        t = tarefaRepository.save(t);
                        if(t instanceof TarefaAutomatica){
                            DomainEvent event = new TarefaAutoCriadaEvent((TarefaAutomatica) t);
                            dispatcher.publish(event);
                        }else{
                            return assignacaoTarefaService.assignarTarefa((TarefaManual) t);
                        }

                    }else {
                        pedido.rejeitar();
                        pedidoRepository.save(pedido);
                    }
                    return true;


                case EM_RESOLUCAO:
                    if(tarefaConcluida.concluidaComSucesso() ){
                        pedido.concluirComSucesso();
                    }else{
                        pedido.concluirSemSucesso();
                    }
                    pedidoRepository.save(pedido);
                    return true;


                default:
                    return false;
            }

        }catch(Exception e){
            LOGGER.error(e);
            return false;
        }

    }





    private TarefaManual criarTarefaDeAprovacao(Pedido pedido){
        return new TarefaManual(pedido, true);
    }


    private Tarefa criarTarefaDeRealizacao(Pedido pedido){

        if(pedido.getServico().requerRealizacaoManual()){
            return   new TarefaManual(pedido, false);
        }else{
            return new TarefaAutomatica(pedido);

        }
    }
}

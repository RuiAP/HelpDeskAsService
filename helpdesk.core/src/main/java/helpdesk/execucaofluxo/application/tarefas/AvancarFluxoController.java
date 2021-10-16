package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.execucaofluxo.domain.tarefas.*;
import helpdesk.execucaofluxo.repositories.TarefaRepository;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.application.pedido.ListarPedidoService;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import java.util.Optional;


public class AvancarFluxoController {

    private final ListarPedidoService pedidoService =  new ListarPedidoService();
    private final TarefaRepository tarefaRepository = PersistenceContext.repositories().tarefas();

    private final AvancarFluxoService avancarFluxoService = new AvancarFluxoService();


    public AvancarFluxoController() {
    }


    public boolean novoPedidoSubmetido(String identificadorPedido){
        Pedido pedido = pedidoService.pedidoPorId(identificadorPedido).orElse(null);
        return pedido != null && novoPedidoSubmetido(pedido);
    }


    public boolean novoPedidoSubmetido(Pedido pedido){
        if (!pedido.estado().equals(EstadoPedido.SUBMETIDO)){
            return false;
        }else{
           return avancarFluxoService.novoPedidoSubmetido(pedido);
        }
    }




    public boolean tarefaRealizada(String codigoTarefa){
        Optional<Tarefa> tarefaOp =tarefaRepository.ofIdentity(CodigoTarefa.valueOf(Integer.parseInt(codigoTarefa)));
        return tarefaOp.isPresent() && tarefaRealizada(tarefaOp.get());
    }


    public boolean tarefaRealizada(Tarefa tarefaConcluida){
        if(tarefaConcluida.estado() != EstadoTarefa.CONCLUIDA){
            return false;
        }else{
            return avancarFluxoService.tarefaConcluida(tarefaConcluida);
        }
    }




}

package helpdesk.solicitacaoservico.application.pedido;

import eapli.framework.application.ApplicationService;
import helpdesk.persistence.PersistenceContext;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@ApplicationService
public class ListarPedidoService {

    PedidoRepository repo = PersistenceContext.repositories().pedidos();

    public String findLastId(){
        Pedido p = repo.findLast();
        return p==null?null:p.identity().toString();
    }

    public Optional<Pedido> pedidoPorId(String identificadorPedido){
        return repo.ofIdentity(IdentificadorPedido.valueOf(identificadorPedido));
    }



    //
    //Sem validação de permissões do utilizador para facilitar a demonstração
    //
    public Iterable<Pedido> todosPedidos(){
        return repo.findAll();
    }

    public Iterable<Pedido> pedidosEmCursoPorData() {
        List< EstadoPedido > estadosEmCurso = new LinkedList<>();
        estadosEmCurso.add(EstadoPedido.EM_RESOLUCAO);
        estadosEmCurso.add(EstadoPedido.EM_APROVACAO);
        estadosEmCurso.add(EstadoPedido.APROVADO);
        estadosEmCurso.add(EstadoPedido.SUBMETIDO);
        return repo.pedidosPorEstadosOrdenadosPorData(estadosEmCurso);
    }

    public Iterable<Pedido> pedidosConcluidosPorData() {
        List<EstadoPedido> estadosConcluidos = new LinkedList<>();
        estadosConcluidos.add(EstadoPedido.CONCLUIDO);
        estadosConcluidos.add(EstadoPedido.FALHOU_REALIZACAO);
        estadosConcluidos.add(EstadoPedido.REJEITADO);
        return repo.pedidosPorEstadosOrdenadosPorData(estadosConcluidos);
    }
}

package helpdesk.solicitacaoservico.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;

import java.util.List;


public interface PedidoRepository extends DomainRepository<IdentificadorPedido, Pedido> {

    Pedido findLast();

    Iterable<Pedido> pedidosPorEstadosOrdenadosPorData(List<EstadoPedido> listaEstados);

}

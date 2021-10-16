package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.solicitacaoservico.domain.pedido.EstadoPedido;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;


public class JpaPedidosRepository extends JpaAutoTxRepository<Pedido, IdentificadorPedido, IdentificadorPedido>
        implements PedidoRepository {

    public JpaPedidosRepository(final TransactionalContext autoTx) {
        super(autoTx, "identificador");
    }

    public JpaPedidosRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "identificador");
    }

    @Override
    public Pedido findLast() {

        final TypedQuery<Pedido> query = createQuery(
                "select p from Pedido p order by identificador desc"
                , Pedido.class);

        Iterable<Pedido> pedidos = query.getResultList();
        if(pedidos.iterator().hasNext())
            return pedidos.iterator().next();

        return  null;

    }

    @Override
    public Iterable<Pedido> pedidosPorEstadosOrdenadosPorData(List<EstadoPedido> estados) {

        final TypedQuery<Pedido> query = createQuery(
                "select p from Pedido p " +
                        "where p.estadoPedido in :estados " +
                        "order by p.dataPedido"
                , Pedido.class);
        query.setParameter("estados", estados);
        return query.getResultList();
    }


}

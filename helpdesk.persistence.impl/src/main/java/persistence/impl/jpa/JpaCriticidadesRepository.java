package persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import helpdesk.Application;
import helpdesk.nivelservico.domain.nivelcriticidade.Designacao;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.solicitacaoservico.domain.pedido.IdentificadorPedido;
import helpdesk.solicitacaoservico.domain.pedido.Pedido;
import helpdesk.solicitacaoservico.repositories.PedidoRepository;

import javax.persistence.TypedQuery;


public class JpaCriticidadesRepository extends JpaAutoTxRepository<NivelCriticidade, Designacao, Designacao>
        implements CriticidadeRepository {

    public JpaCriticidadesRepository(final TransactionalContext autoTx) {
        super(autoTx, "designacao");
    }

    public JpaCriticidadesRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "designacao");
    }

}

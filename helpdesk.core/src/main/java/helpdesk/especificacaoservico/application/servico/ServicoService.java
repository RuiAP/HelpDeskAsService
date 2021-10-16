package helpdesk.especificacaoservico.application.servico;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.persistence.PersistenceContext;
import helpdesk.usermanager.domain.UserRoles;

import java.util.Optional;

public class ServicoService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ServicoRepository servicoRepo = PersistenceContext.repositories().servicos();

    /**
     * Returns the card balance of the authenticated cafeteria user.
     *
     * @return
     */
    public Servico adicionaServico(Servico servico) {
        authz.ensureAuthenticatedUserHasAnyOf(UserRoles.USER_GSH);
        //servico.defineEspecificacaoComoCompleto();
        return servicoRepo.save(servico);
    }

}

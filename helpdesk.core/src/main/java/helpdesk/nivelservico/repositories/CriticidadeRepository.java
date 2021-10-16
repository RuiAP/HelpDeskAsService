package helpdesk.nivelservico.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.nivelservico.domain.nivelcriticidade.Designacao;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;

public interface CriticidadeRepository extends DomainRepository<Designacao, NivelCriticidade> {

}

package helpdesk.especificacaoservico.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.domain.equipa.Equipa;

public interface CatalogoRepository extends DomainRepository<Identificador, Catalogo> {

    Iterable<Catalogo> catalogosDisponiveisPorEquipa(Equipa equipa);

    Iterable<Catalogo> catalogosPorColaborador(NrMecanografico nrMecanografico);
}

package helpdesk.especificacaoservico.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;


public interface ServicoRepository extends DomainRepository<CodigoServico, Servico> {

    Iterable<Servico> findByEstado(EstadoEspecificacao estadoEspecificacao);

    Iterable<Servico> findByEstado(EstadoEspecificacao estadoEspecificacao, NrMecanografico nrMecanografico);

    Iterable<Servico> findByCatalogo(Identificador idCatalogo, NrMecanografico nrMecanografico);

    Iterable<Servico> findByKeyword(Keyword keyword, NrMecanografico nrMecanografico);

    Iterable<Servico> findByTitulo(Titulo titulo, NrMecanografico nrMecanografico);
}

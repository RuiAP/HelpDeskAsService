package helpdesk.especificacaoservico.application.servico;

import eapli.framework.application.ApplicationService;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.domain.servico.*;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.persistence.PersistenceContext;

import java.util.Optional;


@ApplicationService
public class ListarServicoService {
    ServicoRepository repo = PersistenceContext.repositories().servicos();


    public Iterable<Servico> todosServicos(){
        return repo.findAll();
    }

    public Iterable<Servico> servicosIncompletos(){
        return repo.findByEstado(EstadoEspecificacao.INCOMPLETO);
    }

    public Iterable<Servico> servicosPorEstado(EstadoEspecificacao estadoEspecificacao, NrMecanografico nrMecanografico){
        return repo.findByEstado(estadoEspecificacao,nrMecanografico);
    }

    public Optional<Servico> servicoById(String codigoServico){ //não tem em conta permissões
        return repo.ofIdentity(CodigoServico.valueOf(codigoServico));
    }

    public Iterable<Servico> servicosPermitidosByCatalogo(Identificador idCatalogo, NrMecanografico nrMecanografico) {
        return repo.findByCatalogo(idCatalogo,nrMecanografico);
    }

    public Iterable<Servico> servicosPermitidosByKeyword(String keyword, NrMecanografico nrMecanografico) {
        return repo.findByKeyword(Keyword.valueOf(keyword),nrMecanografico);
    }

    public Iterable<Servico> servicosPermitidosByTitulo(String titulo, NrMecanografico nrMecanografico) {
        return repo.findByTitulo(Titulo.valueOf("%"+titulo+"%"), nrMecanografico);//% para devolver nao titulos iguais mas titulos que contêm
    }
}

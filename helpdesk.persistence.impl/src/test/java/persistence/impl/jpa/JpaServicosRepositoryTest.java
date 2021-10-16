package persistence.impl.jpa;

import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.domain.servico.EstadoEspecificacao;
import helpdesk.especificacaoservico.domain.servico.Keyword;
import helpdesk.especificacaoservico.domain.servico.Servico;
import helpdesk.especificacaoservico.domain.servico.Titulo;
import helpdesk.especificacaoservico.repositories.ServicoRepository;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.persistence.PersistenceContext;
import junit.framework.TestCase;

public class JpaServicosRepositoryTest extends TestCase {

//    ServicoRepository repo =  PersistenceContext.repositories().servicos();

    public void testFindByEstado() {
//        Iterable<Servico> servicos = repo.findByEstado(EstadoEspecificacao.INCOMPLETO, NrMecanografico.valueOf("1001"));
//        servicos.forEach(s -> System.out.println(s.identity()));
    }

    public void testFindByCatalogo() {
//        Iterable<Servico> servicos = repo.findByCatalogo(Identificador.valueOf("CAT2021-2"), NrMecanografico.valueOf("1001"));
//        servicos.forEach(s -> System.out.println(s.identity()));
    }

    public void testFindByKeyword() {
//        Iterable<Servico> servicos = repo.findByKeyword(Keyword.valueOf("faturas"), NrMecanografico.valueOf("1001"));
//        servicos.forEach(s -> System.out.println(s.identity()));
    }

    public void testFindByTitulo() {
//        Iterable<Servico> servicos = repo.findByTitulo(Titulo.valueOf("%Sub%"), NrMecanografico.valueOf("1001"));
//        servicos.forEach(s -> System.out.println(s.identity()));
    }
}
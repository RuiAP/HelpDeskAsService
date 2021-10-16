package persistence.impl.jpa;


import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;

public class JpaCatalogoRepositoryTest {

//    CatalogoRepository repo =  PersistenceContext.repositories().catalogos();

    @Before
    public void setUp() {
    }

    @Test
    public void catalogosPorColaborador() {
//        Iterable<Catalogo> catalogos = repo.catalogosPorColaborador(NrMecanografico.valueOf("1003"));
//        catalogos.forEach(c -> System.out.println(c.identity()));
    }

}
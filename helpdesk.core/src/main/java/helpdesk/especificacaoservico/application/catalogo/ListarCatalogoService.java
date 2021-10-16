package helpdesk.especificacaoservico.application.catalogo;

import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ListarCatalogoService {

    private final CatalogoRepository catalogoRepository;

    public ListarCatalogoService() {
        this.catalogoRepository = PersistenceContext.repositories().catalogos();
    }

    public Iterable<Catalogo> catalogosPorColaborador(Colaborador colaborador) {
        return catalogoRepository.catalogosPorColaborador(colaborador.identity());
    }

    public Iterable<Catalogo> todosCatalogos() {
        return catalogoRepository.findAll();
    }

    public Optional<Catalogo> catalogoByIdentificador(String identificador){
        return catalogoRepository.ofIdentity(Identificador.valueOf(identificador));
    }

}

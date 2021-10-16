package helpdesk.especificacaoservico.application.catalogo;

import eapli.framework.application.ApplicationService;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.persistence.PersistenceContext;

@ApplicationService
public class CriarCatalogoService {

    CatalogoRepository repo = PersistenceContext.repositories().catalogos();

    public Catalogo save(Catalogo catalogo){
        return  repo.save(catalogo);
    }

}

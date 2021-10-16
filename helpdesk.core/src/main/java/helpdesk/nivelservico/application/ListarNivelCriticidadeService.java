package helpdesk.nivelservico.application;

import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.especificacaoservico.domain.catalogo.Identificador;
import helpdesk.especificacaoservico.repositories.CatalogoRepository;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ListarNivelCriticidadeService {

    private final CriticidadeRepository repository;

    public ListarNivelCriticidadeService() {
        this.repository = PersistenceContext.repositories().criticidades();
    }

    public Iterable<NivelCriticidade> todosNiveisCriticidade() {
        return repository.findAll();
    }

}

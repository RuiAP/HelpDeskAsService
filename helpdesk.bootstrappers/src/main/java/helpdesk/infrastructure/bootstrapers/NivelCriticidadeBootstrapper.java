package helpdesk.infrastructure.bootstrapers;

import eapli.framework.actions.Action;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;
import helpdesk.nivelservico.repositories.CriticidadeRepository;
import helpdesk.persistence.PersistenceContext;

import java.awt.*;


public class NivelCriticidadeBootstrapper implements Action {

    private final CriticidadeRepository repo = PersistenceContext.repositories().criticidades();

    @Override
    public boolean execute() {

        NivelCriticidade na = new NivelCriticidade("Alto", "Nível alto", 9, Color.red.getRGB(), "Execução em 1 hora", "");
        NivelCriticidade nm = new NivelCriticidade("Médio", "Nível médio", 5, Color.yellow.getRGB(), "Execução em 1 dia", "");
        NivelCriticidade nb = new NivelCriticidade("Baixo", "Nível baixo", 1, Color.green.getRGB(), "Execução em 1 semana", "");

        NivelCriticidade sa = repo.save(na);
        System.out.println(sa);
        NivelCriticidade sb = repo.save(nm);
        System.out.println(sb);
        NivelCriticidade sc = repo.save(nb);
        System.out.println(sc);

        return true;
    }
}

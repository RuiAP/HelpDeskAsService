package helpdesk.estruturaorganica.application.colaborador;

import eapli.framework.application.ApplicationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.colaborador.EmailInstitucional;
import helpdesk.estruturaorganica.domain.colaborador.NrMecanografico;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationService
public class ListarColaboradorService {

    private final ColaboradorRepository repo = PersistenceContext.repositories().colaboradores();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    public ListarColaboradorService() {
    }



    public Colaborador colaboradorAtual() {
        Optional<Colaborador> opColab=  authorizationService.session()
                .flatMap(s -> colaboradorByEmail(s.authenticatedUser().email().toString()));

        if(opColab.isEmpty()){
            throw new IllegalArgumentException("O systemUser atual não tem Colaborador ou Equipa associado.");
        }else{
            return opColab.get();
        }
    }


    public Iterable<Colaborador> todosColaboradores(){
        return repo.findAll();
    }

    public Optional<Colaborador> colaboradorByNrMecanografico(String nrMecanografico){
        return repo.ofIdentity(NrMecanografico.valueOf(nrMecanografico));
    }

    public Optional<Colaborador> colaboradorByEmail(String emailInstitucional){
        return repo.colaboradorByEmail(EmailInstitucional.valueOf(emailInstitucional));
    }

}

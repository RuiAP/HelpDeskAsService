package helpdesk.estruturaorganica.application.colaborador;

import eapli.framework.application.UseCaseController;
import eapli.framework.validations.Preconditions;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.persistence.PersistenceContext;

/**
 *
 * @author Asus
 */
@UseCaseController
public class GestaoColaboradorEquipaController {

    ColaboradorRepository repoColab = PersistenceContext.repositories().colaboradores();
    ListarEquipaService listarEquipaService = new ListarEquipaService();
    ListarColaboradorService listarColabSvc = new ListarColaboradorService();

    public Colaborador adicionarColaboradorAEquipa(Colaborador colaborador, Equipa equipa){
        Preconditions.noneNull(colaborador,equipa);
        if( !colaborador.addEquipa(equipa) ){
            return null;
        }
        return repoColab.save(colaborador);
    }

    public Colaborador removerColaboradorDeEquipa(Colaborador colaborador, Equipa equipa){
        Preconditions.noneNull(colaborador,equipa);
        if( !colaborador.removeEquipa(equipa) ){
          return null;
        }
        return repoColab.save(colaborador);
    }
    
    public Iterable<Equipa> listarEquipasColab(Colaborador colab) {
        Colaborador colaborador =  listarColabSvc.colaboradorByNrMecanografico(colab.identity().value()).get();
        return colaborador.getSetEquipas();
    }

    public Iterable<Equipa> todasEquipas(){
        return listarEquipaService.todasEquipas();
    }

    public Iterable<Colaborador> listarTodosColaboradores(){
        return listarColabSvc.todosColaboradores();
    }
}

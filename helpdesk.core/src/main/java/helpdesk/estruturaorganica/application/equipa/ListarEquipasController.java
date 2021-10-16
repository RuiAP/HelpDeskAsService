package helpdesk.estruturaorganica.application.equipa;

import eapli.framework.application.UseCaseController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.CodigoEquipa;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.persistence.PersistenceContext;

import java.util.Optional;


@UseCaseController
public class ListarEquipasController {

    EquipaRepository equipaRepo = PersistenceContext.repositories().equipas();

    public  Iterable<Equipa> findAll(){
        return equipaRepo.findAll();
    }

    public Optional<Equipa> findEquipaByCodigo(String codigo){
       return equipaRepo.ofIdentity(CodigoEquipa.valueOf(codigo));
    }

}

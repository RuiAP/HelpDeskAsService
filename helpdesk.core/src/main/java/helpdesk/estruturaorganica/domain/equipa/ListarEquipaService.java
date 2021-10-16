package helpdesk.estruturaorganica.domain.equipa;

import eapli.framework.application.ApplicationService;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationService
public class ListarEquipaService {

    EquipaRepository repo = PersistenceContext.repositories().equipas();


    public Iterable<Equipa> todasEquipas(){
        return repo.findAll();
    }

    public Optional<Equipa> equipaByCodigoEquipa(String codigoEquipa){
        return repo.ofIdentity(CodigoEquipa.valueOf(codigoEquipa));
    }
}

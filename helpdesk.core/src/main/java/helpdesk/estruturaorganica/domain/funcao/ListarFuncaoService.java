/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.domain.funcao;

import eapli.framework.application.ApplicationService;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.persistence.PersistenceContext;
import java.util.Optional;

/**
 *
 * @author Asus
 */
@ApplicationService
public class ListarFuncaoService {
    FuncaoRepository repo = PersistenceContext.repositories().funcoes();


    public Iterable<Funcao> todasFuncoes(){
        return repo.findAll();
    }

    public Optional<Funcao> funcao(Designacao designacao){
        return repo.ofIdentity(designacao);
    }
}

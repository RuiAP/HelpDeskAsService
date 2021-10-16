/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.funcao;

import eapli.framework.application.UseCaseController;
import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.persistence.PersistenceContext;

/**
 *
 * @author Asus
 */
@UseCaseController
public class CriarFuncaoController {
    
    FuncaoRepository repo = PersistenceContext.repositories().funcoes();
    
    public Funcao CriarFuncao(String designacao) {
        Funcao funcao = new Funcao(designacao);
        
        return repo.save(funcao);
    }
    
    public Iterable<Funcao> findAll() {
        return repo.findAll();
    }
}

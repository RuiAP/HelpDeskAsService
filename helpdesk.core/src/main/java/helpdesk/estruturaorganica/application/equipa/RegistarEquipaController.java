/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.equipa;

import eapli.framework.application.UseCaseController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;
import helpdesk.estruturaorganica.repositories.EquipaRepository;
import helpdesk.estruturaorganica.repositories.TipoEquipaRepository;
import helpdesk.persistence.PersistenceContext;

/**
 *
 * @author danie
 */
@UseCaseController
public class RegistarEquipaController {


    EquipaRepository equipaRepo = PersistenceContext.repositories().equipas();
    ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    TipoEquipaRepository tipoEquipaRepo = PersistenceContext.repositories().tipoEquipas();


    public Iterable<Colaborador>  colaboradoresPossiveis(){
        return listarColaboradorService.todosColaboradores();
    }

    public Equipa RegistarEquipa(String codigo, String acronimo, String designacao, Colaborador colaboradorReponsavel, TipoEquipa tipo) {
        Equipa equipa = new Equipa(codigo, acronimo, designacao, colaboradorReponsavel, tipo);
        return  equipaRepo.save(equipa);
    }

    public Iterable<TipoEquipa> todosTiposEquipas(){
        return tipoEquipaRepo.findAll();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpdesk.estruturaorganica.application.colaborador;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;
import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.estruturaorganica.domain.funcao.ListarFuncaoService;
import helpdesk.estruturaorganica.repositories.ColaboradorRepository;
import helpdesk.estruturaorganica.repositories.FuncaoRepository;
import helpdesk.persistence.PersistenceContext;
import helpdesk.usermanager.domain.UserRoles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Asus
 */
@UseCaseController
public class RegistarColaboradorController {
    
    ColaboradorRepository repo = PersistenceContext.repositories().colaboradores();    
    FuncaoRepository funcRepo = PersistenceContext.repositories().funcoes();
    ListarFuncaoService funcSvc = new ListarFuncaoService();
    ListarEquipaService listarEquipaService = new ListarEquipaService();
    ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    public RegistarColaboradorController() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(UserRoles.USER_RRH, UserRoles.USER_ADMIN);
    }

    public Colaborador RegistarColaborador(String nomeCurto, String nomeCompleto, String contactoTelefonico, String emailInstitucional, String estadoCivil,
                                           String nif, String nib, String nrMecanografico, Funcao funcao, Colaborador responsavel,
                                           String distrito, Calendar dataNascimento, String password) {
        
        Colaborador colaborador = new Colaborador(nomeCurto, nomeCompleto, contactoTelefonico, emailInstitucional, estadoCivil, nif, nib,
        nrMecanografico, funcao, responsavel, distrito,dataNascimento, password);

        return repo.save(colaborador);

    }
    
    public Colaborador RegistarColaboradorImportado(String nomeCurto, String nomeCompleto, String contactoTelefonico, String emailInstitucional, String estadoCivil,
                                           String nif, String nib, String nrMecanografico, String designacao, String distrito,
                                           String codigoPostal, String localidade, int dia, int mes, int ano, String password) {
        Calendar data = new GregorianCalendar();
        data.set(1900+ano, mes, dia);

        //TODO: Procurar função na base de dados e inserir em função do código
        Funcao funcao = new Funcao(designacao);

        Colaborador colaborador = new Colaborador(nomeCurto, nomeCompleto, contactoTelefonico, emailInstitucional, estadoCivil, nif, nib,
        nrMecanografico, funcao, null, distrito, data, password);
        
        return repo.save(colaborador);
    }
    
    public Iterable<Equipa> listarEquipas(){
        return listarEquipaService.todasEquipas();
    }

    public Iterable<Colaborador> listarColaboradores(){
        return listarColaboradorService.todosColaboradores();
    }

    public Iterable<Funcao> listarFuncoes(){
        return funcRepo.findAll();
    }
}

package helpdesk.infrastructure.bootstrapers;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.validations.Invariants;
import helpdesk.estruturaorganica.application.colaborador.GestaoColaboradorEquipaController;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.application.colaborador.RegistarColaboradorController;
import helpdesk.estruturaorganica.application.equipa.ListarEquipasController;
import helpdesk.estruturaorganica.application.equipa.RegistarEquipaController;
import helpdesk.estruturaorganica.application.funcao.CriarFuncaoController;
import helpdesk.estruturaorganica.application.tipoequipa.RegistarTipoEquipaController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.estruturaorganica.domain.tipoEquipa.TipoEquipa;
import helpdesk.usermanager.application.AddUserController;
import helpdesk.usermanager.domain.UserRoles;

import java.awt.*;
import java.util.*;

public class EstruturaOrganicaBootstrapper implements Action {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();
    private final RegistarTipoEquipaController registarTipoEquipaController = new RegistarTipoEquipaController();
    private final RegistarEquipaController registarEquipaController = new RegistarEquipaController();
    private final ListarEquipasController listarEquipasController = new ListarEquipasController();
    private RegistarColaboradorController registarColaboradorController;
    private final ListarColaboradorService listarColaboradorService = new ListarColaboradorService();
    private final GestaoColaboradorEquipaController gestaoColaboradorEquipaController = new GestaoColaboradorEquipaController();
    private final CriarFuncaoController criarFuncaoController = new CriarFuncaoController();
    private final AddUserController addUserController = new AddUserController();

    private final String passwrodGenerica = "Password1";

    private TipoEquipa tipoEquipaRH, tipoEquipaFinancas, tipoEquipaSoftware;
    private Equipa equipaRH, equipaFC, equipaSoftware;
    private Colaborador colaborador1, colaborador2, colaborador3, colaborador4;


    @Override
    public boolean execute() {
        authenticateForBootstrapping();
        registarColaboradorController = new RegistarColaboradorController();
        novoColaborador();
        novosTiposEquipa();
        novaEquipa();
        associarColaboradorAEquipa();
        return true;
    }



    private  void novoColaborador(){
        System.out.println("\nColaboradores criados:");
        Calendar dataNascimento = new GregorianCalendar();

        Funcao funcao1 = criarFuncaoController.CriarFuncao("Diretor Financeiro");
        dataNascimento.set(1985, Calendar.DECEMBER,10);
        colaborador1 = registarColaboradorController.RegistarColaborador("Joao", "Joao Dias",
                "910000000", "joaodias@gmail.com", "Solteiro",
                "111222333","0035000000000000", "1001",
                funcao1, null, "Porto",
                dataNascimento, "Password1");
        criarUserParaColaborador("Joao Dias","joaodias@gmail.com", passwrodGenerica, "1001");
        System.out.println(colaborador1);

        Funcao funcao4 = criarFuncaoController.CriarFuncao("Técnico Financeiro");
        dataNascimento.set(1985, Calendar.DECEMBER,10);
        colaborador4 = registarColaboradorController.RegistarColaborador("Diana", "Ribeiro",
                "910000000", "dianaribeiro@gmail.com", "Solteiro",
                "111222444","0035000000000000", "1004",
                funcao1, colaborador1, "Porto",
                dataNascimento, "Password1");
        criarUserParaColaborador("Diana Ribeiro","dianaribeiro@gmail.com", passwrodGenerica, "1004");
        System.out.println(colaborador4);

        Funcao funcao2 = criarFuncaoController.CriarFuncao("Director Recursos Humanos");
        dataNascimento.set(1992, Calendar.JULY,10);
        colaborador2 = registarColaboradorController.RegistarColaborador("José", "José Alberto",
                "910000001", "zeberto@gmail.com", "Solteiro",
                "111333444","0035000000000001", "1002",
                funcao2, colaborador4, "Porto",
                dataNascimento, "Password1");
        criarUserParaColaborador("Jose Alberto","zeberto@gmail.com", passwrodGenerica, "1002");
        System.out.println(colaborador2);

        Funcao funcao3 = criarFuncaoController.CriarFuncao("Engenheiro Informático");
        dataNascimento.set(1999, Calendar.MARCH,10);
        colaborador3 = registarColaboradorController.RegistarColaborador("Maria", "Maria Pereira",
                "931222000", "mariaP@gmail.com", "Casado",
                "222333444","0035000000000003", "1003",
                funcao3, colaborador2, "Lisboa",
                dataNascimento, "Password1");
        criarUserParaColaborador("Maria Pereira","mariaP@gmail.com", passwrodGenerica, "1003");
        System.out.println(colaborador3);

    }

    private void criarUserParaColaborador(String nomeCompleto, String emailInstitucional, String password, String nrMecanografico){
        String nomes[] = nomeCompleto.split(" ");
        Set<Role> roles = new HashSet<>();
        roles.add(UserRoles.USER_UTILIZADOR);
        addUserController.addUser(nrMecanografico, password,nomes[0], nomes[nomes.length-1],emailInstitucional, roles);
    }

    private void novosTiposEquipa(){
        System.out.println("\nTipos de equipas criados:");
        tipoEquipaRH = registarTipoEquipaController.TipoEquipa("RH", "Recursos Humanos", new Color(0x7BD458));
        System.out.println(tipoEquipaRH);
        tipoEquipaFinancas = registarTipoEquipaController.TipoEquipa("FC", "Finanças e Contabilidade", new Color(0x700428));
        System.out.println(tipoEquipaFinancas);
        tipoEquipaSoftware = registarTipoEquipaController.TipoEquipa("SW", "Desenvolvimento de Software", new Color(0x100428));
        System.out.println(tipoEquipaSoftware);

    }


    private void novaEquipa(){
        System.out.println("\nEquipas criadas:");

        Optional<Colaborador> colabResponsavel_RH = listarColaboradorService.colaboradorByNrMecanografico("1002");
        if(colabResponsavel_RH.isPresent()){
            equipaRH = registarEquipaController.RegistarEquipa("TEAMRH", "TRH", "Equipa dos recursos humanos", colabResponsavel_RH.get(), tipoEquipaRH);
            System.out.println(equipaRH);
        }else{
            System.out.println("Erro ao recuperar colaborador 1002");
        }


        Optional<Colaborador> colabResponsavel_FC = listarColaboradorService.colaboradorByNrMecanografico("1001");
        if(colabResponsavel_FC.isPresent()){
            equipaFC = registarEquipaController.RegistarEquipa("TEAMFC", "FC", "Equipa de Finanças e Contabilidade", colabResponsavel_FC.get(), tipoEquipaRH);
            System.out.println(equipaFC);
        }else{
            System.out.println("Erro ao recuperar colaborador 1001");
        }

        Optional<Colaborador> colabResponsavel_Software = listarColaboradorService.colaboradorByNrMecanografico("1003");
        if(colabResponsavel_Software.isPresent()){
            equipaSoftware = registarEquipaController.RegistarEquipa("TEAMSW", "SW", "Equipa de desenvolvimento de software", colabResponsavel_Software.get(), tipoEquipaRH);
            System.out.println(equipaSoftware);
        }else{
            System.out.println("Erro ao recuperar colaborador 1003");
        }

    }

    private void associarColaboradorAEquipa(){
        System.out.println("\nAssociações a equipas: ");

        Optional<Colaborador> opColabResponsavel_RH = listarColaboradorService.colaboradorByNrMecanografico("1002");
        Optional<Equipa> opEquipaRh = listarEquipasController.findEquipaByCodigo("TEAMRH");
        if(opColabResponsavel_RH.isPresent() && opEquipaRh.isPresent()){
            gestaoColaboradorEquipaController.adicionarColaboradorAEquipa(opColabResponsavel_RH.get(),opEquipaRh.get());
        }else{
            System.out.println("Erro associar colaborador 1002 à equipa TEAMRH");
        }
        System.out.println("Colaborador 1002 ---> TEAMRH");


        Optional<Colaborador> opColabResponsavel_FC = listarColaboradorService.colaboradorByNrMecanografico("1001");
        Optional<Equipa> opEquipaFC = listarEquipasController.findEquipaByCodigo("TEAMFC");
        if(opColabResponsavel_FC.isPresent() && opEquipaFC.isPresent()){
            gestaoColaboradorEquipaController.adicionarColaboradorAEquipa(opColabResponsavel_FC.get(),opEquipaFC.get());
        }else{
            System.out.println("Erro associar colaborador 1001 à equipa TEAMFC");
        }
        System.out.println("Colaborador 1001 ---> TEAMFC");

        Optional<Colaborador> opColab_FC = listarColaboradorService.colaboradorByNrMecanografico("1004");
        if(opColabResponsavel_FC.isPresent() && opEquipaFC.isPresent()){
            gestaoColaboradorEquipaController.adicionarColaboradorAEquipa(opColab_FC.get(),opEquipaFC.get());
        }else{
            System.out.println("Erro associar colaborador 1004 à equipa TEAMFC");
        }
        System.out.println("Colaborador 1004 ---> TEAMFC");


        Optional<Colaborador> opColabResponsavel_Software = listarColaboradorService.colaboradorByNrMecanografico("1003");
        Optional<Equipa> opEquipaSW = listarEquipasController.findEquipaByCodigo("TEAMSW");
        if(opColabResponsavel_Software.isPresent() && opEquipaSW.isPresent()){
            gestaoColaboradorEquipaController.adicionarColaboradorAEquipa(opColabResponsavel_Software.get(),opEquipaSW.get());
        }else{
            System.out.println("Erro associar colaborador 1003 à equipa TEAMSW");
        }
        System.out.println("Colaborador 1003 ---> TEAMSW");
    }

    protected void authenticateForBootstrapping() {
        authenticationService.authenticate("rrh", "Password1");
        Invariants.ensure(authz.hasSession());
    }
}

package helpdesk.infrastructure.bootstrapers;


import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Invariants;
import helpdesk.especificacaoservico.application.catalogo.CriarCatalogoController;
import helpdesk.especificacaoservico.domain.catalogo.Catalogo;
import helpdesk.estruturaorganica.application.colaborador.ListarColaboradorService;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.equipa.Equipa;
import helpdesk.estruturaorganica.domain.equipa.ListarEquipaService;
import helpdesk.nivelservico.domain.nivelcriticidade.NivelCriticidade;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EspecificacaoCatalogoBootstrapper implements Action {


    private final CriarCatalogoController catalogoController = new CriarCatalogoController();

    private final ListarEquipaService equipaService = new ListarEquipaService();
    private final ListarColaboradorService colaboradorService = new ListarColaboradorService();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();


    @Override
    public boolean execute() {
        authenticateForBootstrapping();

        System.out.println("\nCatálogos criados:");


        Iterable<NivelCriticidade> niveis = catalogoController.todosNiveisCriticidade();

        //Reponsavel é o diretor de RH
        //Todas as equipas têm acesso
        Catalogo catalogo1 = criarCatalogo("CAT2021-1", "Início de funções",
        "Serviços relacionados com o inicio de atividade na empresa ",
        "Pedidos de credenciais de acesso, requisiçao de material, duvidas relacionadas com o contrato de trabalho, etc.",
        "icone do Catalogo", equipaService.todasEquipas(),colaboradorPorNrMecanografico("1002"), niveis.iterator().next());
        System.out.println(catalogo1);

        //Reponsavel é o diretor de RH
        //Todas as equipas têm acesso
        Catalogo catalogo3 = criarCatalogo("CAT2021-3", "Gestão Recursos Humanos",
                "Gestão Recursos Humanos",
                "Serviços relacionados com gestão de recursos huamnos",
                "faltas.ico", equipaService.todasEquipas(),colaboradorPorNrMecanografico("1002"), niveis.iterator().next());
        System.out.println(catalogo3);

        //Reponsavel é o diretor de RH
        //Todas as equipas têm acesso

        Catalogo catalogo4 = criarCatalogo("CAT2021-4", "Vendas",
                "Gestão de Vendas",
                "Gestão de Vendas",
                "descontos.ico", equipaPorCodigoEquipa("TEAMFC"),
                //colaboradorPorNrMecanografico("1001")
                colaboradorService.todosColaboradores()
                , niveis.iterator().next());
        System.out.println(catalogo4);


//        Catalogo catalogo5 = criarCatalogo("CAT2021-5", "Residência",
//                "Gestão de Residências",
//                "Registo e alteração de residência",
//                "descontos.ico", equipaPorCodigoEquipa("TEAMFC"),colaboradorPorNrMecanografico("1001"), niveis.iterator().next());
//        System.out.println(catalogo5);


        //Reponsavel é o diretor financeiro
        //Só a equipa "Finanças e Contabilidade" tem acesso
        Catalogo catalogo2 =criarCatalogo("CAT2021-2", "Gestão de Salários",
        "Serviços relacionados com salários e operações financeiras",
        "Alteração da conta ordenado, re-impressão do recibo de vencimentos, solicitação da declaração de rendimentos anula, etc.",
        "icone catalogo",equipaPorCodigoEquipa("TEAMFC"), colaboradorPorNrMecanografico("1001"), niveis.iterator().next());
        System.out.println(catalogo2);
                //re-impressao recibo de vencimentos
                //Declaração de rendimentos anual


        return catalogo1!= null && catalogo2 != null;
    }


    private Iterable<Equipa> equipaPorCodigoEquipa(String codigoEquipa){
        Set<Equipa> equipasComAcesso = new HashSet<>();
        Optional<Equipa> equipa = equipaService.equipaByCodigoEquipa(codigoEquipa);
        if(equipa.isPresent()){
            equipasComAcesso.add(equipa.get());
            return equipasComAcesso;
        }else{
            throw new IllegalArgumentException("Equipa para acesso ao catálogo não encontrada na base de dados.");
        }
    }



    private Iterable<Colaborador> colaboradorPorNrMecanografico(String nrMecanografico){
        Set<Colaborador> colaboradoresResponsaveis = new HashSet<>();
        Optional<Colaborador> colab = colaboradorService.colaboradorByNrMecanografico(nrMecanografico);
        if(colab.isPresent()){
            colaboradoresResponsaveis.add(colab.get());
            return colaboradoresResponsaveis;
        }else{
            throw new IllegalArgumentException("Colaborador responsável para o catálogo não encontrado na base de dados.");
        }
    }


    private Catalogo criarCatalogo(String id, String titulo, String descBreve, String descCompleta, String icone,
                                   Iterable<Equipa> equipasComAcesso, Iterable<Colaborador> colaboradoresResponsaveis, NivelCriticidade nivel){

        catalogoController.atribuiParametros(id, titulo, descBreve, descCompleta, icone);
        equipasComAcesso.forEach(catalogoController::atribuiAcessoAEquipa);
        colaboradoresResponsaveis.forEach(catalogoController::atribuiColaboradorResponsavel);
        catalogoController.atribuiNivelCriticidade(nivel);
        catalogoController.build();

        return catalogoController.guardaCatalogo();
    }



    protected void authenticateForBootstrapping() {
        authenticationService.authenticate("gsh", "Password1");
        Invariants.ensure(authz.hasSession());
    }

}
package helpdesk.app.backoffice.console.presentation.colaborador;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import helpdesk.app.backoffice.console.presentation.catalogo.ColaboradorPrinter;
import helpdesk.estruturaorganica.application.colaborador.RegistarColaboradorController;
import helpdesk.estruturaorganica.domain.colaborador.Colaborador;
import helpdesk.estruturaorganica.domain.funcao.Funcao;
import helpdesk.usermanager.application.AddUserController;
import helpdesk.usermanager.domain.UserRoles;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class EspecificarColaboradorUI extends AbstractUI {
    
    RegistarColaboradorController registarColabController;
    AddUserController addUserController = new AddUserController();

    public EspecificarColaboradorUI(){
        this.registarColabController = new RegistarColaboradorController();
    }
    
    @Override
    protected boolean doShow() {
        System.out.println("\nIntroduza os dados solicitados para especificar um colaborador:");


        final String nrMecanografico = Console.readLine("Numero mecanografico");
        final String nomeCurto = Console.readLine("Nome curto");
        final String nomeCompleto = Console.readLine("Nome completo");
        final String contactoTelefonico = Console.readLine("Contacto Telefonico");
        final String emailInstitucional = Console.readLine("Email institucional");
        final String distrito = Console.readLine("Distrito");
        final Calendar date = Console.readCalendar("Data de nascimento (dd-MM-yyyy)");
        final String estadoCivil = Console.readLine("Estado civil");
        final String nif = Console.readLine("Nif");
        final String nib = Console.readLine("Nib");


        final Iterable<Funcao> funcoes = this.registarColabController.listarFuncoes();

        final SelectWidget<Funcao> selectorFuncoes = new SelectWidget<>("Escolha a função do colaborador:", funcoes,
                new FuncaoPrinter());
        selectorFuncoes.show();
        final Funcao funcaoSelecionada = selectorFuncoes.selectedElement();
        
        final Iterable<Colaborador> colaboradores = this.registarColabController.listarColaboradores();

        final SelectWidget<Colaborador> selectorColaboradores = new SelectWidget<>("Selecione, caso exista, um colaborador responsável(opcional):", colaboradores,
                new ColaboradorPrinter());
        selectorColaboradores.show();
        final Colaborador colaboradorSelecionado = selectorColaboradores.selectedElement();

        //--------------------------------
        //FIXME Criar colaborador e criar user têm de ser um caso de uso à parte ou estar na mesma transaction!! reutilizar AddUserUI/SignUpUI!?
        System.out.println("Poderá ter acesso ao sistema usando o seu numero mecanográfico como username e uma password.");
        final String password = Console.readNonEmptyLine("Por favor defina a password a utilizar:", "A definição de uma password é obrigatória para prosseguir");
        String nomes[] = nomeCompleto.split(" ");
        Set<Role> roles = new HashSet<>();
        roles.add(UserRoles.USER_UTILIZADOR);
        addUserController.addUser(nrMecanografico, password,nomes[0], nomes[nomes.length-1],emailInstitucional, roles);
        //------------------------------


        Colaborador colaboradorCriado =  registarColabController.RegistarColaborador(nomeCurto, nomeCompleto, contactoTelefonico, emailInstitucional, estadoCivil, nif, nib,
        nrMecanografico, funcaoSelecionada, colaboradorSelecionado, distrito, date, password);

        boolean resultado = colaboradorCriado!=null;
        if(resultado){
            System.out.println("Colaborador criado com sucesso:");
            System.out.println(colaboradorCriado+"\n");
        }else{
            System.out.println("Erro ao criar colaborador.");
        }

        return colaboradorCriado!= null;
    }

    @Override
    public String headline() {
        return "Especificar novo colaborador";
    }
}

package helpdesk.app.bootstrap;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;
import helpdesk.Application;
import helpdesk.app.common.console.HelpDeskAppTemplate;
import helpdesk.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import helpdesk.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import helpdesk.clientusermanagement.domain.events.SignupAcceptedEvent;
import helpdesk.infrastructure.bootstrapers.*;
import helpdesk.persistence.PersistenceContext;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;
import helpdesk.usermanager.application.eventhandlers.SignupAcceptedWatchDog;
import helpdesk.usermanager.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.util.ArrayPredicates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Bootstrapping data app
 *
 */
@SuppressWarnings("squid:S106")
public final class BaseBootstrap extends HelpDeskAppTemplate {
    /**
     * avoid instantiation of this class.
     */
    private BaseBootstrap() {

    }

    private static final Logger LOGGER = LogManager.getLogger(BaseBootstrap.class);


    private boolean incluirEspecificacao;


    /**
     * To allow for the bootstrap to be run from other consoles application to facilitate
     * the development process.
     */
    boolean embeddedFromOtherConsoles;

    public static void main(final String[] args) {

        Application.settings().setAction("drop-and-create");

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());


        new BaseBootstrap().run(args);
    }

    /*
    Method Overriden so the system.exit() was called conditionally according to embeddedFromOtherConsoles
     */
    @Override
    public void run(final String[] args) {
        printHeader();

        try {

            doMain(args);
            Application.settings().setAction("none");

            printFooter();
        } catch (final Exception e) {
            System.out.println(
                    "Ocorreu um erro durante a execução do bootstrap!\n");
            LOGGER.error(e);
        } finally {
            clearEventHandlers(InProcessPubSub.dispatcher());
        }

        // exiting the application, closing all threads
        if(!embeddedFromOtherConsoles){
            System.exit(0);
        }
    }

    @Override
    protected void doMain(final String[] args) {
        handleArgs(args);

        final List<Action> actions = new ArrayList<>();
        actions.add(new BaseBootstrapper());
        actions.add(new UsersBootstrapper());
        actions.add(new EstruturaOrganicaBootstrapper());
        actions.add(new NivelCriticidadeBootstrapper());
        if(incluirEspecificacao){
            actions.add(new EspecificacaoCatalogoBootstrapper());
            actions.add(new EspecificacaoServicoBootstrapper());
            actions.add(new SolicitacaoServicoBootstraper());
        }


         final List<String> separators = new ArrayList<>();
        separators.add("\n\n------- ADMIN ROLE --------");
        separators.add( "\n\n------- USER ROLES -------");
        separators.add( "\n\n------- ESTRUTURA ORGÂNICA -------");
        separators.add( "\n\n------- NIVEIS CRITICIDADE -------");
        if(incluirEspecificacao){
            separators.add("\n\n------- ESPECIFICAÇÃO CATÁLOGOS -------");
            separators.add( "\n\n------- ESPECIFICAÇÃO SERVIÇOS -------");
            separators.add( "\n\n------- SOLICITAÇÃO SERVIÇOS -------");
        }

        final List<Boolean> results = new ArrayList<>();


        assert(actions.size() == separators.size());

        for(int i = 0; i<actions.size(); i++){
            System.out.println(separators.get(i));
            results.add( actions.get(i).execute());
        }

        for(Boolean result: results){
            if(!result){
                System.out.println("\n\n== Bootstrap concluído com *erros* ! ==");
            }
        }
        System.out.println("\n\n== Bootstrap concluído com sucesso ==");

        if(MotorFluxoClient.isConnected()){
            MotorFluxoClient.closeConnection();
        }
    }

    private void handleArgs(final String[] args) {
        embeddedFromOtherConsoles = ArrayPredicates.contains(args, "-embedded");
        incluirEspecificacao = !ArrayPredicates.contains(args, "-SoEstrutura");
    }

    @Override
    protected String appTitle() {
        return "Bootstrapping Helpdesk data ";
    }

    @Override
    protected void printFooter() {
        System.out.println(SEPARATOR_HR);
        System.out.println(SEPARATOR_HR);
        System.out.println("\n");
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(), NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}

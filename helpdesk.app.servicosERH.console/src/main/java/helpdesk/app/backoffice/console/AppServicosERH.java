package helpdesk.app.backoffice.console;

import helpdesk.Application;
import helpdesk.app.backoffice.console.presentation.FrontMenu;
import helpdesk.app.bootstrap.BaseBootstrap;
import helpdesk.app.common.console.HelpDeskAppTemplate;
import helpdesk.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import helpdesk.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import helpdesk.clientusermanagement.domain.events.SignupAcceptedEvent;
import helpdesk.persistence.PersistenceContext;
import helpdesk.usermanager.application.eventhandlers.SignupAcceptedWatchDog;
import helpdesk.usermanager.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;

@SuppressWarnings("squid:S106")
public final class AppServicosERH extends HelpDeskAppTemplate {

    /**
     * avoid instantiation of this class.
     */
    private AppServicosERH() {
    }


    /**
     * @param args
     *            the command line arguments
     */
    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());


        BaseBootstrap.main(new String[]{"-embedded"});


        new AppServicosERH().run(args);
    }

    @Override
    protected void doMain(final String[] args) {

        new FrontMenu().show();
//        // login and go to main menu
//        if (new LoginUI().show()) {
//            // go to main menu
//            final MainMenu menu = new MainMenu();
//            menu.mainLoop();
//        }
    }

    @Override
    protected String appTitle() {
        return Application.settings().getProperty("application.name") + " application - Portal de Serviços e Recursos Humanos";
    }



    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(),
                NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}

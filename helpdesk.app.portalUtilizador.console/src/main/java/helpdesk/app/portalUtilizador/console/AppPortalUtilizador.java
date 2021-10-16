package helpdesk.app.portalUtilizador.console;

import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import helpdesk.Application;
import helpdesk.app.bootstrap.BaseBootstrap;
import helpdesk.app.common.console.HelpDeskAppTemplate;
import helpdesk.app.portalUtilizador.console.presentation.FrontMenu;
import helpdesk.persistence.PersistenceContext;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;
import helpdesk.usermanager.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.client.RestTemplate;


@SuppressWarnings("squid:S106")
@SpringBootApplication
public class AppPortalUtilizador extends HelpDeskAppTemplate {


    public static void main(String[] args){
        SpringApplication.run(AppPortalUtilizador.class,args);

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());



        BaseBootstrap.main(new String[]{"-embedded"});

        new AppPortalUtilizador().run(args);
    }

    @Override
    protected void doMain(String[] args) {
        new FrontMenu().show();
    }

    @Override
    protected String appTitle() {
        return Application.settings().getProperty("application.name") + " application - Portal do Utilizador";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }


}

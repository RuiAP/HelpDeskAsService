package helpdesk.motorFluxos;

import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;
import helpdesk.Application;
import helpdesk.execucaofluxo.domain.tarefas.TarefaAutoCriadaEvent;
import helpdesk.protocoloComunicacao.HelpDeskProtocolServer;
import helpdesk.motorFluxos.presentation.TarefaAutomaticaWatchDog;
import helpdesk.persistence.PersistenceContext;
import helpdesk.protocoloComunicacao.parsers.MotorFluxoParser;
import helpdesk.usermanager.domain.BasePasswordPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MotorFluxoAtividade {

    private static final int PORT = Integer.parseInt(Application.settings().getProperty("motorfluxoServer.portNumber"));
    private static final Logger LOGGER = LogManager.getLogger(MotorFluxoAtividade.class);


    private MotorFluxoAtividade() {
    }

    public static void main(final String[] args) {

        LOGGER.info("Configuring the daemon - Motor de Fluxos de Atividades");

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(),
                new PlainTextEncoder());

        setupEventHandlers();
        LOGGER.info("Starting the server socket");
        final HelpDeskProtocolServer server = new HelpDeskProtocolServer();

        System.setProperty("javax.net.ssl.keyStore", Application.settings().getProperty("motorFluxoServer.keystore.location"));
        System.setProperty("javax.net.ssl.keyStorePassword", Application.settings().getProperty("motorFluxoServer.keystore.password"));


        System.setProperty("javax.net.ssl.trustStore", Application.settings().getProperty("common.truststore.location"));
        System.setProperty("javax.net.ssl.trustStorePassword",Application.settings().getProperty("common.truststore.password"));

        server.start(PORT, new MotorFluxoParser(), true);

        LOGGER.info("Exiting the daemon - Motor de Fluxos de Atividades");
        System.exit(0);
    }



    @SuppressWarnings("unchecked")
    protected static void setupEventHandlers() {
        InProcessPubSub.dispatcher().subscribe(new TarefaAutomaticaWatchDog(), TarefaAutoCriadaEvent.class);
    }
}

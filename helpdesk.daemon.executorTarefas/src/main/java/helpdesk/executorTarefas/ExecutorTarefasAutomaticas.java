package helpdesk.executorTarefas;


import helpdesk.Application;
import helpdesk.protocoloComunicacao.HelpDeskProtocolServer;
import helpdesk.protocoloComunicacao.parsers.ExecutorTarefasParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecutorTarefasAutomaticas {

    private static final String PORT = (Application.settings().getProperty("executorTarefas.portNumber"));
    private static final Logger LOGGER = LogManager.getLogger(ExecutorTarefasAutomaticas.class);


    private ExecutorTarefasAutomaticas() {
    }

    public static void main(final String[] args) {


        //default port
        String split[] = PORT.split(";");
        int port = Integer.parseInt(split[0]);

        //args port
        try {
            port = Integer.parseInt(split[Integer.parseInt(args[0])]);
        }catch (Exception ex){
            LOGGER.error("Argumento inválido: Porta definida por defeito");
        }

        LOGGER.info("Configuring the daemon - Motor de Tarefas Automáticas (" + port+")" );

        System.setProperty("javax.net.ssl.keyStore", Application.settings().getProperty("executorTarefas.keystore.location"));
        System.setProperty("javax.net.ssl.keyStorePassword", Application.settings().getProperty("executorTarefas.keystore.password"));


        System.setProperty("javax.net.ssl.trustStore", Application.settings().getProperty("common.truststore.location"));
        System.setProperty("javax.net.ssl.trustStorePassword",Application.settings().getProperty("common.truststore.password"));


        LOGGER.info("Starting the server socket");
        final HelpDeskProtocolServer server = new HelpDeskProtocolServer();
        server.start(port, new ExecutorTarefasParser(),true);

        LOGGER.info("Exiting the daemon - Executor de Tarefas Automáticas");
        System.exit(0);
    }

}

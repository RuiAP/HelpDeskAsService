package helpdesk.app.common.console;

import helpdesk.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;

/**
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public abstract class HelpDeskAppTemplate {

    // we are assuming we will always use the in process event
    // dispatcher. check the Spring version of the Base project
    // for an alternative
    final EventDispatcher dispatcher = InProcessPubSub.dispatcher();

    protected static final String SEPARATOR_HR = "============================================";
    private static final Logger LOGGER = LogManager.getLogger(HelpDeskAppTemplate.class);

    /**
     * @param args
     *            the command line arguments
     */
    public void run(final String[] args) {
        printHeader();

        try {

            doMain(args);

            printFooter();
        } catch (final Exception e) {
            System.out.println(
                    "Something unexpected has happened and the application will terminate. Please check the logs.\n");
            LOGGER.error(e);
        } finally {
            clearEventHandlers(dispatcher);
        }

        // exiting the application, closing all threads
        System.exit(0);
    }

    protected void printFooter() {
        System.out.println("\n");
        System.out.println(SEPARATOR_HR);
        System.out.println("Thanks for using "+ Application.settings().getProperty("application.name"));

        System.out.println(SEPARATOR_HR);
    }

    protected void printHeader() {
        System.out.println("\n");
        System.out.println(SEPARATOR_HR);
        System.out.println(appTitle());
        System.out.println(SEPARATOR_HR);
    }



    private final void setupEventHandlers() {
        doSetupEventHandlers(dispatcher);
    }


    protected abstract void doMain(final String[] args);

    protected abstract String appTitle();



    protected void clearEventHandlers(final EventDispatcher dispatcher) {
        // nothing to do
    }

    protected abstract void doSetupEventHandlers(EventDispatcher dispatcher);
}




package helpdesk.linguagem;

import eapli.framework.util.Utility;
import helpdesk.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Utility
public final class InterpretadorContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterpretadorContext.class);

    private static Interpretador interpretadorClass;

    private InterpretadorContext() {
        // ensure utility
    }

    public static Interpretador interpretador() {
        if (interpretadorClass == null) {
            final String interpretadorClass = Application.settings().getInterpretadorClass();
            try {
                InterpretadorContext.interpretadorClass = (Interpretador) Class.forName(interpretadorClass).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                LOGGER.error("Unable to dynamically load the Gramatica Class", ex);
                throw new IllegalStateException(
                        "Unable to dynamically load the Gramatica Class: " + interpretadorClass, ex);
            }
        }
        return interpretadorClass;
    }
}

package helpdesk.execucaofluxo.application.tarefas.execucaoautomatica;

import eapli.framework.util.Utility;
import helpdesk.Application;
import helpdesk.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Utility
public class SeletorDeAlgoritmos {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    private static DistribuicaoTarefasAutomaticas algoritmo;

    private SeletorDeAlgoritmos() {
        // ensure utility
    }

    public static DistribuicaoTarefasAutomaticas loadFromProperties() {
        if (algoritmo == null) {
            final String algoritmoClassName = Application.settings().getAlgoritmoAssignacaoExecutor();
            try {
                algoritmo = (DistribuicaoTarefasAutomaticas) Class.forName(algoritmoClassName).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                LOGGER.error("Unable to dynamically load the Task Assignment Algorithm", ex);
                throw new IllegalStateException(
                        "Unable to dynamically load the RTask Assignment Algorithm: " + algoritmoClassName, ex);
            }
        }
        return algoritmo;
    }
}

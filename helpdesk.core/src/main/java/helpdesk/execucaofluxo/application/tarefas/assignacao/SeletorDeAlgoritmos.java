package helpdesk.execucaofluxo.application.tarefas.assignacao;

import eapli.framework.util.Utility;
import helpdesk.Application;
import helpdesk.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Utility
public class SeletorDeAlgoritmos {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    private static AlgoritmoAssignacao algoritmo;

    private SeletorDeAlgoritmos() {
        // ensure utility
    }

    public static AlgoritmoAssignacao loadFromProperties() {
        if (algoritmo == null) {
            final String algoritmoClassName = Application.settings().getAlgoritmoAssignacaoTarefas();
            try {
                algoritmo = (AlgoritmoAssignacao) Class.forName(algoritmoClassName).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                LOGGER.error("Unable to dynamically load the Task Assignment Algorithm", ex);
                throw new IllegalStateException(
                        "Unable to dynamically load the RTask Assignment Algorithm: " + algoritmoClassName, ex);
            }
        }
        return algoritmo;
    }

    public static AlgoritmoAssignacao fcfs(){
        return new AssignacaoTarefaFCFS();
    }

    public static AlgoritmoAssignacao porTrabalhoPendente(){
        return new AssignacaoTarefaFCFS();
    }
}

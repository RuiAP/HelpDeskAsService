package helpdesk.execucaofluxo.application.tarefas;

import helpdesk.linguagem.Interpretador;
import helpdesk.linguagem.InterpretadorContext;
import helpdesk.linguagem.ScriptResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ExecutorDeTarefasController {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorDeTarefasController.class);

    private final Interpretador interpretador = InterpretadorContext.interpretador();

    public ExecutorDeTarefasController() {
    }


    public ScriptResult executarTarefaAutomatica(Map<String, String> nomeVariavel_valor, String script){

        return interpretador.runScriptRealizacao(nomeVariavel_valor, script);
    }

}

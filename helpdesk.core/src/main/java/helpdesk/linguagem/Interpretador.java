package helpdesk.linguagem;

import java.util.Map;

public interface Interpretador {

    ScriptResult runScript(Map<String, Object> nomeVariavel_valor, String script);

    ScriptResult runScriptRealizacao(Map<String, String> nomeVariavel_valor, String script);

}

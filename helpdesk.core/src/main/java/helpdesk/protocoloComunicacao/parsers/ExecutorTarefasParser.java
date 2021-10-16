package helpdesk.protocoloComunicacao.parsers;

import helpdesk.execucaofluxo.application.tarefas.ExecutorDeTarefasController;
import helpdesk.protocoloComunicacao.HelpDeskProtocolMessageParser;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.requests.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static helpdesk.protocoloComunicacao.ProtocolCodes.*;
import static helpdesk.protocoloComunicacao.ProtocolCodes.CodigoInvalido;

public class ExecutorTarefasParser implements HelpDeskProtocolMessageParser {



    private static final Logger LOGGER = LogManager.getLogger(MotorFluxoParser.class);

    private static ExecutorDeTarefasController executorTarefasControlelr;

    public ExecutorTarefasParser() {

    }


    /**
     * Parse and build the request.
     *
     * @return
     */
    public HelpDeskProtocolRequest parse(int codigo, String mensagem) {
        HelpDeskProtocolRequest request;

        if (codigo == RealizarTarefaAutomatica.codigo()) {
            return  parseExecutarTarefa(codigo,mensagem);
        }
        else if (codigo == EstadoExecutorTarefas.codigo()) {
            request = parseEstadoExecutor(codigo, mensagem);
        }else if (codigo == Fim.codigo()) {
            request = HelpDeskProtocolRequest.createRequest(Entendido.codigo(), "");
        }else{
            request = HelpDeskProtocolRequest.createRequest(CodigoInvalido.codigo(),
                    "Código utilizado não tem comportamento definido.");
        }
        return request;
    }




    private static HelpDeskProtocolRequest parseExecutarTarefa(int codigo, String mensagem) {
        Map<String, String> mapaVariaveis = new HashMap<>();
        try{
            String[] dados = mensagem.split("--");
            String codigoTarefa = dados[0];
            String script = dados[1];
            for(int i = 2; i<dados.length-1; i=i+2){
                mapaVariaveis.put(dados[i], dados[i+1]);
            }
            return new ExecutarTarefaRequest(codigo,codigoTarefa, getExecutorTarefasController(),mapaVariaveis, script);

        }catch (Exception e){
            return HelpDeskProtocolRequest.createRequest(ErroDeFormatacao.codigo(), "Dados necessários para realizar a Tarefa ausentes ou mal formatados.");
        }

    }

    private static HelpDeskProtocolRequest parseEstadoExecutor(int codigo, String mensagem) { //validar formato da mensagem
        return new EstadoExecutorRequest(codigo, mensagem, getExecutorTarefasController());
    }




    private static final Object lock = new Object();

    private static ExecutorDeTarefasController getExecutorTarefasController() {
        synchronized (lock) {
            if (ExecutorTarefasParser.executorTarefasControlelr != null) {
                return ExecutorTarefasParser.executorTarefasControlelr;
            }
        }
        return new ExecutorDeTarefasController();
    }




}

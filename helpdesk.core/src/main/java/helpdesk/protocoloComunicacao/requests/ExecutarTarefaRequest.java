package helpdesk.protocoloComunicacao.requests;

import helpdesk.execucaofluxo.application.tarefas.ExecutorDeTarefasController;
import helpdesk.linguagem.ScriptResult;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;

import java.util.Map;

public class ExecutarTarefaRequest extends HelpDeskProtocolRequest {

    private final ExecutorDeTarefasController controller;

    private final Map<String, String> mapaVariaveis;
    private final String codigoTarefa;

    public ExecutarTarefaRequest(int codigo, String codigoTarefa,ExecutorDeTarefasController controller,Map<String, String> mapaVariaveis, String script ) {
        super(codigo, script);
        this.controller = controller;
        this.mapaVariaveis = mapaVariaveis;
        this.codigoTarefa = codigoTarefa;
    }

    @Override
    public byte[] execute() {
        ScriptResult resultado = controller.executarTarefaAutomatica(mapaVariaveis, mensagem);
        if(resultado.foiExecutado() && resultado.executouComSucesso()){
            return ProtocolMessage.construirResposta(ProtocolCodes.FeedbackRealizacaoTarefa.codigo(), resultado.getMensagem());
        }else{
            return ProtocolMessage.construirResposta(ProtocolCodes.ErroAoRealizarPedido.codigo(), resultado.getMensagem());
        }

    }


}

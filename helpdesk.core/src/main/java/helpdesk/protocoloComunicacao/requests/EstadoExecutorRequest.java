package helpdesk.protocoloComunicacao.requests;

import helpdesk.execucaofluxo.application.tarefas.ExecutorDeTarefasController;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;

public class EstadoExecutorRequest extends HelpDeskProtocolRequest {

    private final ExecutorDeTarefasController controller;

    public EstadoExecutorRequest(int codigo, String mensagem, ExecutorDeTarefasController controller) {
        super(codigo, mensagem);
        this.controller = controller;
    }

    @Override
    public byte[] execute() {
        return ProtocolMessage.construirResposta(ProtocolCodes.ErroDoServidor.codigo(), "funcionalidade não implementada");
    }
}

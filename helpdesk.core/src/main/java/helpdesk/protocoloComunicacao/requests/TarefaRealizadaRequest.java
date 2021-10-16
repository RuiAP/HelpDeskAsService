package helpdesk.protocoloComunicacao.requests;

import helpdesk.execucaofluxo.application.tarefas.AvancarFluxoController;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TarefaRealizadaRequest extends HelpDeskProtocolRequest {

    private static final Logger LOGGER = LogManager.getLogger(TarefaRealizadaRequest.class);
    protected final AvancarFluxoController avancarFluxoController;

    public TarefaRealizadaRequest(int codigo, String mensagem, AvancarFluxoController controller) {
        super(codigo, mensagem);
        this.avancarFluxoController = controller;
    }

    @Override
    public byte[] execute() {

        try {
            String resposta = "";
            if(avancarFluxoController.tarefaRealizada(super.mensagem)){
                return ProtocolMessage.construirResposta(ProtocolCodes.RealizadoComSucesso.codigo(), resposta+mensagem);
            }else{
                return ProtocolMessage.construirResposta(ProtocolCodes.ErroAoRealizarPedido.codigo(), resposta+mensagem);
            }


        } catch (final IllegalArgumentException e) {
            return HelpDeskProtocolRequest.erroDeConteudo().execute();
        } catch (final Exception e) {
            LOGGER.error(e);
            return HelpDeskProtocolRequest.erroDeConteudo().execute();
        }
    }

}
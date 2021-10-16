package helpdesk.protocoloComunicacao.requests;

import helpdesk.execucaofluxo.application.tarefas.AvancarFluxoController;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PedidoSubmetidoRequest extends HelpDeskProtocolRequest {

    private static final Logger LOGGER = LogManager.getLogger(PedidoSubmetidoRequest.class);


    protected final AvancarFluxoController avancarFluxoController;

    public PedidoSubmetidoRequest(int codigo, final String mensagem, AvancarFluxoController controller) {
        super(codigo,mensagem);
        this.avancarFluxoController = controller;
    }

    @Override
    public byte[] execute() {

        try {
            if(avancarFluxoController.novoPedidoSubmetido(super.mensagem) ){
                return ProtocolMessage.construirResposta(ProtocolCodes.RealizadoComSucesso.codigo(), "Fluxo do pedido "+super.mensagem+ " foi iniciado");
            }else{
                return ProtocolMessage.construirResposta(ProtocolCodes.ErroAoRealizarPedido.codigo(), super.mensagem);
            }

        } catch (final IllegalArgumentException e) {
            return HelpDeskProtocolRequest.erroDeConteudo().execute();
        } catch (final Exception e) {
            LOGGER.error(e);
            return HelpDeskProtocolRequest.erroDeConteudo().execute();
        }
    }

}
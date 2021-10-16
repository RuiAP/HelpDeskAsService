package helpdesk.protocoloComunicacao.parsers;

import helpdesk.execucaofluxo.application.tarefas.AvancarFluxoController;
import helpdesk.execucaofluxo.application.tarefas.DashboardController;
import helpdesk.protocoloComunicacao.HelpDeskProtocolMessageParser;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.requests.DrashboardTarefaRequest;
import helpdesk.protocoloComunicacao.requests.PedidoSubmetidoRequest;
import helpdesk.protocoloComunicacao.requests.TarefaRealizadaRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static helpdesk.protocoloComunicacao.ProtocolCodes.*;

public class MotorFluxoParser implements HelpDeskProtocolMessageParser {


    private static final Logger LOGGER = LogManager.getLogger(MotorFluxoParser.class);

    private static AvancarFluxoController fluxoController;
    private static DashboardController dashboardController;

    public MotorFluxoParser() {

    }


    /**
     * Parse and build the request.
     *
     * @return
     */
    public HelpDeskProtocolRequest parse(int codigo, String mensagem) {
        HelpDeskProtocolRequest request;

         if (codigo == PedidoSubmetido.codigo()) {
            return  parseNovoPedidoRealizado(codigo,mensagem);
        }
        else if (codigo == InformacaoDeTarefas.codigo()) {
            request = parseDashBoardTarefa(codigo,mensagem);
        }
        else if (codigo == TarefaConcluida.codigo()) {
            request = parseTarefaRealizada(codigo, mensagem);
        }
        else if (codigo == Fim.codigo()) {
            request = HelpDeskProtocolRequest.createRequest(Entendido.codigo(), "");
        }else{
            request = HelpDeskProtocolRequest.createRequest(CodigoInvalido.codigo(),
                    "Código utilizado não tem comportamento definido.");
        }
        return request;
    }




    private static HelpDeskProtocolRequest parseNovoPedidoRealizado(int codigo, String mensagem) { //validar formato da mensagem
        return new PedidoSubmetidoRequest(codigo,mensagem, getFluxoController());
    }

    private static HelpDeskProtocolRequest parseDashBoardTarefa(int codigo, String mensagem) { //validar formato da mensagem
        return new DrashboardTarefaRequest(codigo, mensagem, getDashboardController());
    }

    private static HelpDeskProtocolRequest parseTarefaRealizada(int codigo, String mensagem) { //validar formato da mensagem
      return new TarefaRealizadaRequest(codigo, mensagem, getFluxoController());

    }




    private static final Object lock = new Object();

    private static AvancarFluxoController getFluxoController() {
        synchronized (lock) {
            if (MotorFluxoParser.fluxoController != null) {
                return MotorFluxoParser.fluxoController;
            }
        }
        return new AvancarFluxoController();
    }

    private static DashboardController getDashboardController() {
        synchronized (lock) {
            if (MotorFluxoParser.dashboardController != null) {
                return MotorFluxoParser.dashboardController;
            }
        }
        return new DashboardController();
    }


}

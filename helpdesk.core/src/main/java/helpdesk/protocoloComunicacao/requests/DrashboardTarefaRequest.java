package helpdesk.protocoloComunicacao.requests;

import helpdesk.execucaofluxo.application.tarefas.DashboardController;
import helpdesk.execucaofluxo.domain.tarefas.Tarefa;
import helpdesk.protocoloComunicacao.HelpDeskProtocolRequest;
import helpdesk.protocoloComunicacao.ProtocolCodes;
import helpdesk.protocoloComunicacao.ProtocolMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DrashboardTarefaRequest extends HelpDeskProtocolRequest {

    private static final Logger LOGGER = LogManager.getLogger(DrashboardTarefaRequest.class);

    protected final DashboardController dashboardController;

    public DrashboardTarefaRequest(int codigo, String mensagem, DashboardController controller) {
        super(codigo, mensagem);
        this.dashboardController = controller;
    }

    @Override
    public byte[] execute() {

        try {
            final Iterable<Tarefa> tarefas = dashboardController.tarefasDoColaborador(super.mensagem);
            StringBuilder resposta = new StringBuilder();
            for (Tarefa t: tarefas) {
                resposta.append(t.dadosDashboard()).append(";");
            }

            return ProtocolMessage.construirResposta(ProtocolCodes.RespostaInformacaoDeTarefas.codigo(), resposta.toString());

        } catch (final IllegalArgumentException e) {
            return HelpDeskProtocolRequest.erroDeConteudo().execute();
        } catch (final Exception e) {
            LOGGER.error(e);
            return HelpDeskProtocolRequest.erroDeConteudo().execute();

        }
    }

}
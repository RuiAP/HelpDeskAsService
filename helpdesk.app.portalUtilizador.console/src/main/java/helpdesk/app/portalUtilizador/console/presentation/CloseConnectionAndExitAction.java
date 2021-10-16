package helpdesk.app.portalUtilizador.console.presentation;

import eapli.framework.actions.Action;
import helpdesk.protocoloComunicacao.clients.MotorFluxoClient;

public class CloseConnectionAndExitAction implements Action {
    private String mensagem;

    public CloseConnectionAndExitAction(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public boolean execute() {

        if(MotorFluxoClient.isConnected()){
            MotorFluxoClient.closeConnection();
        }
        System.out.println(mensagem);
        return true;
    }
}

package helpdesk.app.portalUtilizador.console.presentation.tarefas;

import eapli.framework.presentation.console.AbstractUI;
import helpdesk.Application;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DashboardTarefasUI extends AbstractUI {


    @Override
    protected boolean doShow() {


        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                String ip = Application.settings().getProperty("httpServer.ip");
                String port = Application.settings().getProperty("httpServer.portNumber");

                Desktop.getDesktop().browse(new URI("https://"+ip+":"+port+"/index.html"));

            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String headline() {
        return "Dashboard de Tarefas";
    }
}

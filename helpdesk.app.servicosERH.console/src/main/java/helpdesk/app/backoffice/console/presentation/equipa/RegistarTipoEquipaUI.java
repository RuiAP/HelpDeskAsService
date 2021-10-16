package helpdesk.app.backoffice.console.presentation.equipa;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import helpdesk.estruturaorganica.application.tipoequipa.RegistarTipoEquipaController;
import java.awt.Color;

public class RegistarTipoEquipaUI extends AbstractUI {

    public RegistarTipoEquipaController controller;

    public RegistarTipoEquipaUI() {
        this.controller = new RegistarTipoEquipaController();
    }

    @Override
    protected boolean doShow() {
        System.out.println("Introduza os dados para criar novo tipo de equipa:");
        final String codigo = Console.readLine("Código");

        final String descricao = Console.readLine("Descrição");

        System.out.println("Tipo de Equipa criado.");

        return controller.TipoEquipa(codigo, descricao, Color.red) != null;
    }

    @Override
    public String headline() {
        return "Registar Novo Tipo de Equipa";
    }
}

package helpdesk.app.backoffice.console.presentation.nivelCriticidade;

import eapli.framework.presentation.console.AbstractUI;

public class DefinirNivelCriticidadeUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        return false;
    }

    @Override
    public String headline() {
        return "Definir niveis de criticidade";
    }
}
